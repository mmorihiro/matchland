package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.alpha
import ktx.actors.plus
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController(
        val onHit: (Int, Pair<Int, Int>, () -> Unit) -> Unit) : Controller {
    override val view = PuzzleView(
            ::touchAction,
            ::onTouchDragged,
            { view -> onTouchUp(view, { _, _, _ -> addNewBubbles() }) }).apply {
        this + backGround
        this + puzzleBackGround
        this + bubbleGroup
        bubbles.forEach { it.forEach { bubbleGroup + it } }
    }

    /*private fun showAction(view: PuzzleView,
                           type: Pair<Int, Int>, size: Int): Unit = view.run {
        cover.alpha = 0f
        this + cover
        cover + (delay(0.2f) then fadeIn(0.2f) then Actions.run {
            resetBubbles()
            val label = createLabel(size)
            val bubble = createBubble(type)
            bubble + (fadeAction() then Actions.run {
                this - bubble
            })
            label + (fadeAction() then Actions.run {
                this - label
                onHit(size, type, this@PuzzleController::resume)
            })
            this + bubble
            this + label
        })
    }

    private fun fadeAction() =
            delay(0.7f) then parallel(fadeOut(0.3f), moveBy(0f, 10f, 0.3f))*/

    fun addNewBubbles(): Unit = view.run {
        bubbles = bubbles.mapIndexed { xIndex, it ->
            val remains = it.filter { it.color == Color.WHITE }
            if (remains.size == 4) remains
            else remains + (0..3 - remains.size).map {
                loader.loadRandom().apply {
                    x = xIndex * tileSize + 8
                    y = tileSize * (4 + it)
                    alpha = 0f
                    this + Actions.fadeIn(0.15f)
                    bubbleGroup + this
                }
            }
        }
        moveToRightPoint()
    }

    private fun moveToRightPoint() = view.run {
        bubbles.forEach {
            it.forEachIndexed { index, bubble ->
                val toY = index * tileSize + 8
                bubble + Actions.moveTo(bubble.x, toY,
                        0.3f * Math.abs(bubble.y - toY) / tileSize,
                        Interpolation.swingOut)
            }
        }
    }

    fun resume() {

    }
}