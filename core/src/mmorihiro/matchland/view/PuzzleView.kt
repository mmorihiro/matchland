package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType.WATER
import mmorihiro.matchland.model.Values

class PuzzleView(override val onTouchDown: (Puzzle, Int, Int) -> Unit,
                 override val onTouchDragged: (Puzzle, Int, Int) -> Unit,
                 override val onTouchUp: (Puzzle) -> Unit) : Puzzle() {
    val pauseButton = Button(Image(asset<Texture>("pause.png")).drawable).apply { 
        x = Values.width - width - 8
        y = Values.height - height - 8
    }
    val level = ConfigModel.config.stageNumber
    override var tiles = (0 until colSize).map { index -> createRow(index) }
    override var items = createItems()

    private fun createItems(): MutableList<MutableList<MyImage>> {
        return tiles.map {
            it.map {
                loadItem().apply {
                    x = it.x + padding
                    y = it.y + padding
                }
            }.toMutableList()
        }.toMutableList()
    }

    fun loadItem() =
            itemLoader.load(listOf(playerType, enemyType, WATER)[MathUtils.random(2)].position)

    private fun createRow(yIndex: Int) = (0 until rowSize).map { xIndex ->
        val itemType = when (yIndex) {
            0 -> playerType
            colSize - 1 -> enemyType
            else ->
                if (yIndex < 2) WATER
                else if (yIndex >= colSize - level / 2 && MathUtils.randomBoolean()) enemyType
                else WATER
        }

        MyImage(Image(asset<Texture>("tile.png")), itemType.position).apply {
            x = 19 + xIndex * tileSize
            y = yIndex * tileSize + bottom
            color = itemType.color
        }
    }
}