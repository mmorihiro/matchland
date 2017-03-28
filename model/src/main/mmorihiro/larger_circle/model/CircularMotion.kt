package mmorihiro.larger_circle.model

import com.badlogic.gdx.scenes.scene2d.actions.RelativeTemporalAction
import java.lang.Math.*


class CircularMotion(private val centerX: Float,
                     private val centerY: Float,
                     duration: Float) : RelativeTemporalAction() {
    init {
        setDuration(duration)
    }

    private var percent = 0f
    private val firstRadian by lazy {
        atan2((actor.x - centerX).toDouble(), (actor.y - centerY).toDouble())
    }
    private val radius by lazy {
        val x = actor.x + actor.width / 2 - centerX
        val y = actor.y + actor.height / 2 - centerY
        sqrt((x * x + y * y).toDouble()).toFloat()
    }

    override fun begin() {
        super.begin()
        percent = 0f
    }

    override fun updateRelative(percentDelta: Float) {
        percent += percentDelta
        val radian = toRadians(360 * percent.toDouble()) + firstRadian
        actor.x = centerX + radius * cos(radian).toFloat() - actor.width / 2
        actor.y = centerY + radius * sin(radian).toFloat() - actor.height / 2
    }
}