package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BattleView

class BattleViewController : ViewController {
    override val view = BattleView().apply {
        viewport.camera.translate(0f, -288f, 0f)
        this + player
        this + enemy
    }
}
