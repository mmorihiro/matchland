package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.*
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController(
        val onHit: (Int, Pair<Int, Int>, () -> Unit) -> Unit) : Controller {
    override val view = PuzzleView().apply {
        this + backGround
        this + puzzleBackGround
        bubbles.forEach { row ->
            moveAction(this, row)
            touchAction(this, row)
        }
        this + bubbleGroup
        this + bar
        onEveryAct {
            if (bubbles.first().isEmpty()) {
                nextRow().let {
                    moveAction(this, it)
                    touchAction(this, it)
                }
            }
        }
        Gdx.input.inputProcessor = this
    }

    private fun touchAction(view: PuzzleView, row: List<Bubble>) = view.run {
        row.forEach { bubble ->
            bubble.onClick { _, _ ->
                val points = bubbles.mapIndexed { yIndex, row ->
                    row.mapIndexed {
                        xIndex, bubble ->
                        (xIndex to yIndex) to bubble.type
                    }
                            .filter { it.second == bubble.type }
                            .filterNot { it.first.second == 4 }
                            .map { it.first }
                }.flatten()
                if (bubble.alpha != 0f) {
                    val group = PuzzleModel().sameTypeGroup(points).find {
                        it.contains(((bubble.x - 8) / tileSize).toInt()
                                to ((bubble.y - 8) / tileSize).toInt())
                    }!!.filter { (x, y) -> bubbles[y][x].alpha != 0f }.toSet()
                    group.forEach { (x, y) -> bubbles[y][x].alpha = 0f }
                    showAction(view, bubble.type, group.size)
                }
            }
        }
    }

    private fun showAction(view: PuzzleView,
                           type: Pair<Int, Int>, size: Int) = view.run {
        this + cover
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
    }

    private fun fadeAction() =
            delay(1.0f) then parallel(fadeOut(0.3f), moveBy(0f, 10f, 0.3f))

    private fun moveAction(view: PuzzleView, row: List<Bubble>) = view.run {
        row.forEach { bubble ->
            bubbleGroup += bubble
            bubble + forever(delay(1.2f) then Actions.run {
                if (cover !in this) {
                    bubble.y -= 48f
                }
                if (bubble.y < 0) {
                    bubble.remove()
                    removeBubble(bubble)
                }
            })
        }
    }

    fun resume() {
        view - view.cover
    }
}