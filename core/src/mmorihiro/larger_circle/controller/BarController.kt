package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView


class BarController(originBullets: Int) : Controller {
    override val view = BarView(originBullets).apply {
        viewport.camera.translate(0f, -448f, 0f)
        bar.onDecrease { remain ->
            percentLabel.setText("$remain")
        }
        bar.onFinish {
            // todo
        }
        this + percentLabel
    }
}