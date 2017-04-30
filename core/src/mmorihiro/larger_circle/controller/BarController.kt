package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView


class BarController(turns: Int, onFinish: (Int) -> Unit) : Controller {
    override val view = BarView(turns, onFinish).apply {
        viewport.camera.translate(0f, -451f, 0f)
        this + life
        this + turnLabel
        this + star
        this + starLabel
    }
}