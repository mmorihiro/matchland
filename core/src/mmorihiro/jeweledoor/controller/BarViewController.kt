package mmorihiro.jeweledoor.controller

import ktx.actors.plus
import mmorihiro.jeweledoor.view.BarView


class BarViewController(originBullets: Int) : ViewController {
    override val view = BarView(originBullets).apply {
        viewport.camera.translate(0f, -280f, 0f)
        bar.onDecrease { remain ->
            percentLabel.setText(
                    "bullets  ${String.format("%02d", remain)}/${bar.origin}")
        }
        this + backGroundBar
        this + bar
        this + percentLabel
    }
}