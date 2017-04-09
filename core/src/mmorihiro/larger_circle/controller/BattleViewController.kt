package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy
import ktx.actors.plus
import mmorihiro.larger_circle.model.BattleModel
import mmorihiro.larger_circle.model.Reaction
import mmorihiro.larger_circle.view.BattleView
import mmorihiro.larger_circle.view.LoadBubbles

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
                val row = rows.last()
                row.bubbles.find {
                    it.circle().contains(bullet.circle())
                }?.let {
                    val reaction =
                            BattleModel().decideReaction(bullet.type, it.type)
                    when (reaction) {
                        Reaction.WIN -> {
                            row.removeBubble(it)
                            false
                        }
                        Reaction.NORMAL -> {
                            row.removeBubble(it)
                            bullet.remove()
                            true
                        }
                        Reaction.DEFEAT -> {
                            bullet.remove()
                            if (it.isDamaged) it.remove()
                            else it.damage()
                            true
                        }
                    }
                } ?: false
            }
        }
        bullet + moveBy(135f, 0f, 0.5f)
    }
}
