package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
import ktx.actors.alpha
import ktx.actors.plus
import mmorihiro.larger_circle.view.BarView
import mmorihiro.larger_circle.view.StarType


class BarController(private val onClear: () -> Unit) : Controller {
    override val view = BarView().apply {
        this + bar
        stars.forEach { this + it }
        bar.width = getPercentWidth(5)
    }

    fun percentEffect(value: Int): Unit = view.run {
        val target = getPercentWidth(value)
        barAction(target, view)
        if (stars.count { it.type == StarType.GET.position } == 3) {
            onClear()
        }
        bar + object : TemporalAction(0.3f) {
            val start = bar.width
            val change = target - start

            override fun update(percent: Float) {
                bar.width = start + change * percent
            }
        }
    }

    private fun barAction(target: Float, view: BarView) = view.run {
        stars = stars.map { star ->
            when {
                star.x + star.width / 2 < bar.x + target
                        && star.type == StarType.GRAY.position -> StarType.GET
                star.x + star.width / 2 > bar.x + target
                        && star.type == StarType.GET.position -> StarType.GRAY
                else -> null
            }?.let {
                star.remove()
                getStar(it).apply {
                    setPosition(star.x, star.y)
                    this@BarController.view + this
                    alpha = 0.5f
                    setScale(1.3f)
                    this + parallel(fadeIn(0.3f),
                            scaleTo(1f, 1f, 0.3f))
                }
            } ?: star
        }
    }
}