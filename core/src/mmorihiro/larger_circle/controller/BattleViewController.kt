package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BattleView

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
        println("hit $type")
    }
}
