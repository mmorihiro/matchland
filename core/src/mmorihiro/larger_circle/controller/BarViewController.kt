package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView


class BarViewController(originBullets: Int) : ViewController {
    override val view = BarView(originBullets).apply {
        bar.onDecrease { remain ->
            percentLabel.setText("$remain")
        }
        this + percentLabel
    }
}