package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.alpha
import ktx.assets.asset
import mmorihiro.larger_circle.model.Values

class PuzzleView(val onTouchDown: (PuzzleView, Int, Int) -> Unit) : View() {
    val tileSize = 48f
    val backGround = Image(asset<Texture>("upBackground.png"))
    val puzzleBackGround = Image(asset<Texture>("background.png"))
    val loader = LoadBubbles(32, "bubbles.png")
    var bubbles = (0..4).map { index ->
        createRow(index).map {
            it.apply {
                if (index != 4) {
                    alpha = 0f
                    y += tileSize * 4f
                }
            }
        }
    }
        private set
    var bubbleGroup = Group()
    val bar = Image(asset<Texture>("bar.png")).apply {
        y = puzzleBackGround.height
    }
    val cover = Image(asset<Texture>("cover.png"))

    private fun createRow(yIndex: Int): List<Bubble> =
            (0..5).map { xIndex ->
                loader.loadRandom().apply {
                    x = xIndex * tileSize + 8
                    y = yIndex * tileSize + 8
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

    override fun touchDown(screenX: Int, screenY: Int,
                           pointer: Int, button: Int): Boolean {
        onTouchDown(this, screenX, Values.height.toInt() - screenY)
        return true
    }
}

