package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.delay
import com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat
import ktx.actors.alpha
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController(onHit: (Pair<Int, Int>) -> Unit) : Controller {
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
                if (bubble.alpha != 0f)
                    PuzzleModel().sameTypeGroup(points).find {
                        it.contains(((bubble.x - 8) / tileSize).toInt()
                                to ((bubble.y - 8) / tileSize).toInt())
                    }?.forEach { (x, y) -> bubbles[y][x].alpha = 0f }
            }
        }
    }

    private fun moveAction(view: PuzzleView, row: List<Bubble>) = view.run {
        row.forEach { bubble ->
            bubbleGroup += bubble
            bubble + repeat(5, delay(1.2f) then Actions.run {
                bubble.y -= 48f
                if (bubble.y < 0) {
                    bubble.remove()
                    removeBubble(bubble)
                }
            })
        }
    }
}