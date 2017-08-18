package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView


class BarController : Controller {
    override val view = BarView().apply {
        this + bar
        bar.width = getPercentWidth(5)
    }

    fun setPercent(value: Int): Unit = view.run {
        bar + object : TemporalAction(0.3f) {
            val start = bar.width
            val change = getPercentWidth(value) - start

            override fun update(percent: Float) {
                bar.width = start + change * percent
            }
        }
    }
}