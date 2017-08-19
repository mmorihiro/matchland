package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.larger_circle.model.Point

class PuzzleView(val onTouchDown: (PuzzleView, Int, Int) -> Unit,
                 val onTouchDragged: (PuzzleView, Int, Int) -> Unit,
                 val onTouchUp: (PuzzleView) -> Unit) : View() {
    val rowSize = 5
    val colSize = 7
    val tileSize = 51f
    val bottom = 55
    val padding = 8
    val backGround = Image(asset<Texture>("background.png"))
    val itemLoader = ImageLoader(32, "items.png")
    var tiles = (0 until colSize).map { index -> createRow(index) }
    var items = tiles.map {
        it.map {
            itemLoader.loadRandom().apply {
                x = it.x + padding
                y = it.y + padding
            }
        }.toMutableList()
    }.toMutableList()
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null

    private fun createRow(yIndex: Int) = (0 until rowSize).map { xIndex ->
        MyImage(Image(asset<Texture>("tile.png")), 0 to 0).apply {
            x = 19 + xIndex * tileSize
            y = yIndex * tileSize + bottom
            color = when (yIndex) {
                0 -> Colors.fire
                colSize - 1 -> Colors.thunder
                else -> Colors.water
            }
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
        return true
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
        return true
    }
}