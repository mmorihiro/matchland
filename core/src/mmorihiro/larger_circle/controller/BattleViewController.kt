package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.BattleModel
import mmorihiro.larger_circle.model.Reaction
import mmorihiro.larger_circle.view.BattleView
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.LoadBubbles
import mmorihiro.larger_circle.view.Row

class BattleViewController : ViewController {
    override val view = BattleView().apply {
        viewport.camera.translate(0f, -288f, 0f)
        this + background
        this + ground
        this + rightGround
        this + cannon
        ropes.forEach { this + it }
        rows.forEach { this + it }
    }

    fun onHit(type: Pair<Int, Int>) {
        val bullet = LoadBubbles(16, 1, "bullets.png").load(type)
        view.run {
            this + bullet
            bullet.setPosition(cannon.x + cannon.imageWidth, cannon.y + 11)
            onAct {
                collision(bullet, rows.last())
            }
        }
        bullet + (moveBy(135f, 0f, 0.5f) then Actions.run { bullet.remove() })
    }

    private fun collision(bullet: Bubble, row: Row): Boolean =
            row.bubbles.find { it.circle().contains(bullet.circle()) }?.let {
                val isFinished = reaction(bullet, it, row)
                if (row.bubbles.isEmpty()) {
                    nextRow(row)
                }
                isFinished
            } ?: false

    private fun reaction(bullet: Bubble, bubble: Bubble, row: Row): Boolean =
            when (BattleModel().decideReaction(bullet.type, bubble.type)) {
                Reaction.WIN -> {
                    row.removeBubble(bubble)
                    false
                }
                Reaction.NORMAL -> {
                    row.removeBubble(bubble)
                    bullet.remove()
                    true
                }
                Reaction.DEFEAT -> {
                    bullet.remove()
                    if (bubble.isDamaged) bubble.remove()
                    else bubble.damage()
                    true
                }
            }

    private fun nextRow(row: Row) =
            view.run {
                removeRow(row)
                newRow(Row.nextRow().apply {
                    bubbles.mapIndexed { index, bubble ->
                        bubble.apply {
                            x = 122 + (width + 6) * index
                            y = -38f - 38f + 10f
                        }
                    }
                })
                rows.forEach {
                    it.bubbles.forEach { it + moveBy(0f, 38f, 0.3f) }
                }
            }
}
