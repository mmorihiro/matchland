package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy
import ktx.actors.plus
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
        bubbles.forEach { this + it }
    }

    fun onHit(type: Pair<Int, Int>) {
        val bullet = LoadBubbles(16, 1, "bullets.png").load(type)
        view.run {
            this + bullet
            bullet.setPosition(cannon.x + cannon.imageWidth, cannon.y + 11)
        }
        bullet + moveBy(135f, 0f, 0.5f)
    }
}
