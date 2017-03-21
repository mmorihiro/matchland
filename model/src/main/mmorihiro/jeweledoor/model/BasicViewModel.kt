package mmorihiro.jeweledoor.model

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils


class BasicViewModel(val jewelSize: Int,
                     val backGroundWidth: Float,
                     val backGroundHeight: Float,
                     val cannonArea: Circle) {
    fun jewelPosition(): Pair<Float, Float> {
        val jewelX = MathUtils.random(backGroundWidth - jewelSize)
        val jewelY = MathUtils.random(backGroundHeight - jewelSize)
        val jewelRadius = jewelSize / 2f
        val jewelArea = Circle(
                jewelX + jewelRadius, jewelY + jewelRadius, jewelRadius)
        return if (cannonArea.overlaps(jewelArea)) {
            jewelPosition()
        } else jewelX to jewelY
    }
}