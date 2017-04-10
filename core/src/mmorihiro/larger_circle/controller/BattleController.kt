package mmorihiro.larger_circle.controller

import mmorihiro.larger_circle.view.BattleView

class BattleController : Controller {
    override val view = BattleView().apply {
        viewport.camera.translate(0f, -288f, 0f)
    }

    fun onHit(type: Pair<Int, Int>) {

    }
}
