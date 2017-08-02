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
    val tileSize = 48f
    val bottom = 53
    val rowSize = 6
    val colSize = 8
    val padding = 8
    val backGround = Image(asset<Texture>("background.png"))
    val itemLoader = ImageLoader(32, "items.png")
    var bubbles = (0..colSize - 1).map { index -> createRow(index) }
    var items = bubbles.map {
        it.map {
            itemLoader.loadRandom().apply {
                x = it.x + padding
                y = it.y + padding
            }
        }
    }
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null

    private fun createRow(yIndex: Int): List<MyImage> {
        val size = if (yIndex % 2 == 0) rowSize - 2 else rowSize - 1
        return (0..size).map { xIndex ->
            MyImage(Image(asset<Texture>("bubble.png")), 0 to 0).apply {
                x = xIndex * tileSize +
                        if (yIndex % 2 == 0) tileSize / 2 else 0f
                y = yIndex * tileSize + bottom - 7 * yIndex
                color = if (yIndex < 4) Color.GREEN else Color.GOLD
            }
        }
    }

    fun coordinateToPoint(x: Int, y: Int): Point {
        val yPoint = ((y - bottom) / (tileSize - 7)).let {
            if (it > 0) it.toInt() else -1
        }
        val xPoint =
                (if (yPoint % 2 == 0) x - tileSize.toInt() / 2 else x) /
                        tileSize.toInt()
        return xPoint to yPoint
    }

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
