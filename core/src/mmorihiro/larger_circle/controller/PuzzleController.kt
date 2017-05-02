package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.Interpolation
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
        this + bubbleGroup
        this + bar
        this + cover
        cover + (delay(3.8f) then Actions.run { this - cover })
        bubbles.forEachIndexed { index, row ->
            row.forEach { bubble ->
                bubbleGroup += bubble
                if (index != 4) {
                    bubble + (delay(index * 0.9f)
                            then moveBy(0f, -tileSize * 4f, 0.9f,
                            Interpolation.bounceOut)) +
                            action {
                                if (bubble.y <= tileSize * 4) {
                                    bubble.alpha = 1f
                                    true
                                } else false
                            }
                }
                bubble + (delay(3.8f) then Actions.run {
                    moveAction(this, bubble)
                    touchAction(this, bubble)
                })
            }
        }
        onEveryAct {
            if (bubbles.first().isEmpty()) {
                nextRow().let {
                    it.forEach { bubble ->
                        bubbleGroup += bubble
                        moveAction(this, bubble)
                        touchAction(this, bubble)
                    }
                }
            }
        }
    }

    private fun touchAction(view: PuzzleView, bubble: Bubble) = view.run {
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
                    it.contains(((bubble.x + 8) / tileSize).toInt()
                            to ((bubble.y + 8) / tileSize).toInt())
                }!!.filter { (x, y) -> bubbles[y][x].alpha != 0f }.toSet()
                group.forEach { (x, y) ->
                    bubbles[y][x] + fadeOut(0.2f)
                }
                showAction(view, bubble.type, group.size)
            }
        }
    }

    private fun moveAction(view: PuzzleView, bubble: Bubble) = view.run {
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

    private fun showAction(view: PuzzleView,
                           type: Pair<Int, Int>, size: Int) = view.run {
        cover.alpha = 0f
        this + cover
        cover + (delay(0.2f) then fadeIn(0.2f) then Actions.run {
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
            delay(0.7f) then parallel(fadeOut(0.3f), moveBy(0f, 10f, 0.3f))

    fun resume() {
        view - view.cover
    }
}