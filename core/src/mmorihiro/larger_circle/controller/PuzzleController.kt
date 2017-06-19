package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController(
        val onHit: (Int, Pair<Int, Int>, () -> Unit) -> Unit) : Controller {
    override val view = PuzzleView(
            ::touchAction,
            ::onTouchDragged,
            { view -> onTouchUp(view, { _, _, _ -> TODO() }) }).apply {
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

    fun resume() {

    }
}