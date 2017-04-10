package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController(onHit: (Pair<Int, Int>) -> Unit) : Controller {
    override val view = PuzzleView().apply {
        this + backGround
        this + puzzleBackGround
        bubbles.forEach { row -> moveAction(this, row) }
        this + bubbleGroup
        this + bar
        onEveryAct {
            if (bubbles.first().isEmpty()) {
                nextRow().let { moveAction(this, it) }
            }
        }
        Gdx.input.inputProcessor = this
    }

    private fun moveAction(view: PuzzleView, row: List<Bubble>) = view.run {
        row.forEach { bubble ->
            bubbleGroup += bubble
            bubble + repeat(5, delay(1.0f) then moveBy(0f, -48f, 0.4f)
                    then Actions.run {
                if (bubble.y < 0) {
                    bubble.remove()
                    removeBubble(bubble)
                }
            })
        }
    }
}
