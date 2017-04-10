package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import ktx.assets.asset

class PuzzleView : View() {
    val tileSize = 48f
    val backGround = Image(asset<Texture>("upBackground.png"))
    val puzzleBackGround = Image(asset<Texture>("background.png"))
    val loader = LoadBubbles(32, "bubbles.png")
    var bubbles = (0..4).map { createRow(it) }
        private set
    var bubbleGroup = Group()
    val bar = Image(asset<Texture>("bar.png")).apply {
        y = puzzleBackGround.height
    }

    private fun createRow(yIndex: Int): List<Bubble> =
            (0..5).map { xIndex ->
                loader.loadRandom().apply {
                    x = xIndex * tileSize + 8
                    y = yIndex * tileSize + 8
                }
            }

    fun nextRow(): List<Bubble> {
        val row = createRow(bubbles.lastIndex)
        bubbles = bubbles.drop(1).plus<List<Bubble>>(row.map {
            this + it
            it
        })
        return row
    }

    fun removeBubble(bubble: Bubble) {
        bubbles = bubbles.map { row -> row - bubble }
    }
}

