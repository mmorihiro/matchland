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
    val tileSize = 51f
    val bottom = 55
    val rowSize = 5
    val colSize = 7
    val padding = 8
    val fireColor = Color(255 / 255f, 193 / 255f, 132 / 255f, 1f)
    val thunderColor = Color(255 / 255f, 255 / 255f, 188 / 255f, 1f)
    val waterColor = Color(147 / 255f, 255 / 255f, 255 / 255f, 0.5f)
    val backGround = Image(asset<Texture>("background.png"))
    val itemLoader = ImageLoader(32, "items.png")
    var tiles = (0..colSize - 1).map { index -> createRow(index) }
    var items = tiles.map {
        it.map {
            itemLoader.loadRandom().apply {
                x = it.x + padding
                y = it.y + padding
            }
        }
    }
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null

    private fun createRow(yIndex: Int) = (0..rowSize - 1).map { xIndex ->
        MyImage(Image(asset<Texture>("tile.png")), 0 to 0).apply {
            x = 17 + xIndex * tileSize
            y = yIndex * tileSize + bottom
            color = when (yIndex) {
                0 -> fireColor
                colSize - 1 -> thunderColor
                else -> waterColor
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
        onTouchDragged(this, pos.first, pos.second)
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int,
                           pointer: Int, button: Int): Boolean {
        val pos = screenPosToWorldPos(screenX, screenY)
        onTouchDown(this, pos.first, pos.second)
        return true
    }
}
