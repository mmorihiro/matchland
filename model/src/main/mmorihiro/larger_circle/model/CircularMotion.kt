package mmorihiro.larger_circle.model

import com.badlogic.gdx.scenes.scene2d.Actor
import java.lang.Math.*


class CircularMotion(private val centerX: Float,
                     private val centerY: Float,
                     private val times: Int,
                     private val actor: Actor) {
    private var count = 0
    private val firstRadian by lazy {
        atan2((actor.x + actor.width / 2 - centerX).toDouble(),
                (actor.y + actor.height / 2 - centerY).toDouble())
    }
    private val radius by lazy {
        val x = actor.x + actor.width / 2 - centerX
        val y = actor.y + actor.height / 2 - centerY
        sqrt((x * x + y * y).toDouble()).toFloat()
    }

    fun next(): Pair<Float, Float> {
        count += 1
        val radian = toRadians(360.0 * count / times + 90) + firstRadian
        val x = centerX + radius * cos(radian).toFloat() - actor.width / 2
        val y = centerY + radius * sin(radian).toFloat() - actor.height / 2
        return x to y
    }
}