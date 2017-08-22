package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.larger_circle.model.ConfigModel
import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.ItemType.WATER
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.Values

class PuzzleView(private val onTouchDown: (PuzzleView, Int, Int) -> Unit,
                 private val onTouchDragged: (PuzzleView, Int, Int) -> Unit,
                 private val onTouchUp: (PuzzleView) -> Unit,
                 val playerType: ItemType,
                 val enemyType: ItemType) : View() {
    val rowSize = 5
    val colSize = 7
    private val tileSize = 51f
    private val bottom = 55
    private val padding = 8
    val pauseButton = Button(Image(asset<Texture>("pause.png")).drawable).apply { 
        x = Values.width - width - 8
        y = Values.height - height - 8
    }
    val level = ConfigModel.config.stageNumber
    val backGround = Image(asset<Texture>("background.png"))
    val itemLoader = ImageLoader(32, "items.png")
    var tiles = (0 until colSize).map { index -> createRow(index) }
    var items = createItems()
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null

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

    fun coordinateToPoint(x: Int, y: Int): Point =
            x / tileSize.toInt() to (y - bottom) / tileSize.toInt()

    fun resetBubbles() {
        items.forEach { it.forEach { it.color = Color.WHITE } }
    }

    override fun touchUp(screenX: Int, screenY: Int,
                         pointer: Int, button: Int): Boolean {
        onTouchUp(this)
        return super.touchUp(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int,
                              pointer: Int): Boolean {
        val pos = screenPosToWorldPos(screenX, screenY)
        if (pos.first > padding && pos.second > bottom)
            onTouchDragged(this, pos.first, pos.second)
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int,
                           pointer: Int, button: Int): Boolean {
        val pos = screenPosToWorldPos(screenX, screenY)
        if (pos.first > padding && pos.second > bottom)
            onTouchDown(this, pos.first, pos.second)
        return super.touchDown(screenX, screenY, pointer, button)
    }
}