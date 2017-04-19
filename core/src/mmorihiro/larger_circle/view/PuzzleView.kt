package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin

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
    val cover = Image(asset<Texture>("cover.png"))

    private fun createRow(yIndex: Int): List<Bubble> =
            (0..5).map { xIndex ->
                loader.loadRandom().apply {
                    x = xIndex * tileSize + 8
                    y = yIndex * tileSize + 8
                }
            }

    fun nextRow(): List<Bubble> =
            createRow(bubbles.lastIndex).also { row ->
                bubbles = bubbles.drop(1) + listOf(row.map {
                    this + it
                    it
                })
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
    }
}

