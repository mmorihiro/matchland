package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.alpha
import ktx.assets.asset
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.Values

class PuzzleView(val onTouchDown: (PuzzleView, Int, Int) -> Unit,
                 val onTouchDragged: (PuzzleView, Int, Int) -> Unit,
                 val onTouchUp: (PuzzleView) -> Unit) : View() {
    val tileSize = 48f
    val bottom = 40
    val rowSize = 6
    val colSize = 8
    val padding = 6
    val backGround = Image(asset<Texture>("background.png"))
    val loader = ItemLoader(32, "items.png")
    var items = (0..rowSize - 1).map { index -> createRow(index) }
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null

    private fun createRow(xIndex: Int): List<Item> {
        return (0..colSize - 1).map { yIndex ->
            loader.loadRandom().apply {
                x = xIndex * tileSize + padding
                y = yIndex * tileSize + padding + bottom
            }
        }
    }

    fun coordinateToPoint(x: Int, y: Int): Point =
            x / tileSize.toInt() to (y - bottom) / tileSize.toInt()

    fun getBubbleFromPoint(point: Point) = items[point.first][point.second]

    fun resetBubbles() {
        items.forEach {
            it.forEach {
                it.color = Color.WHITE
                it.alpha = 1.0f
            }
        }
    }

    /*fun nextRow(): List<Item> =
            createRow(items.lastIndex).also { row ->
                items = items.drop(1) + listOf(row)
            }

     fun removeBubble(bubble: Item) {
         items = items.map { row -> row - bubble }
     }
 
     fun createLabel(number: Int) =
             Label(" x $number", Scene2DSkin.defaultSkin).apply {
                 centerPosition(288f, 192f)
                 x += 10f
             }
 
     fun createBubble(type: Pair<Int, Int>) = loader.load(type).apply {
         centerPosition(288f, 192f)
         x -= 25f
     }*/

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
