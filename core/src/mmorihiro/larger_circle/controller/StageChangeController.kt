package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.StageChangeView


class StageChangeController(onFinish: () -> Unit) : Controller {
    override val view = StageChangeView(onFinish).apply {
        this + label
    }
}