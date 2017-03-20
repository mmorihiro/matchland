package mmorihiro.jeweledoor.controller

import ktx.actors.plus
import mmorihiro.jeweledoor.view.BarView


class BarViewController(originBullets: Int) : ViewController {
    override val view = BarView(originBullets).apply {
        viewport.camera.translate(0f, -288f, 0f)
        this + backGroundBar
        this + bar
    }
}