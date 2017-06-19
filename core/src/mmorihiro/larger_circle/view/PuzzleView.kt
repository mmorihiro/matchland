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
    val backGround = Image(asset<Texture>("upBackground.png"))
    val puzzleBackGround = Image(asset<Texture>("background.png"))
    val loader = LoadBubbles(32, "bubbles.png")
    var bubbles = (0..5).map { index -> createRow(index) }
        private set
    var bubbleGroup = Group()
    var connectEvent: ConnectEvent? = null

    private fun createRow(xIndex: Int): List<Bubble> =
            (0..3).map { yIndex ->
                loader.loadRandom().apply {
                    x = xIndex * tileSize + 8
                    y = yIndex * tileSize + 8
                }
            }

    fun coordinateToPoint(x: Int, y: Int): Point =
            x / tileSize.toInt() to y / tileSize.toInt()

    fun getBubbleFromPoint(point: Point) = bubbles[point.first][point.second]

    fun resetBubbles() {
        bubbles.forEach {
            it.forEach {
                it.color = Color.WHITE
                it.alpha = 1.0f
            }
        }
    }

    /*fun nextRow(): List<Bubble> =
            createRow(bubbles.lastIndex).also { row ->
                bubbles = bubbles.drop(1) + listOf(row)
            }

     fun removeBubble(bubble: Bubble) {
         bubbles = bubbles.map { row -> row - bubble }
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
        val y = Values.height.toInt() - screenY
        if (y < tileSize * 4) onTouchDragged(this, screenX, y)
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int,
                           pointer: Int, button: Int): Boolean {
        val y = Values.height.toInt() - screenY
        if (y < tileSize * 4) onTouchDown(this, screenX, y)
        return true
    }
}