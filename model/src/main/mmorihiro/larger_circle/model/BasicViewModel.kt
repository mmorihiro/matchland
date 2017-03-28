package mmorihiro.larger_circle.model

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils


class BasicViewModel(val jewelSize: Int,
                     val backGroundWidth: Float,
                     val backGroundHeight: Float,
                     val cannonArea: Circle) {
    fun jewelPosition(area: Int,
                      total: Int): Pair<Float, Float> {
        val xRange = (backGroundWidth - jewelSize) / total
        val jewelX = MathUtils.random(xRange * (area % total),
                xRange * (area % total + 1) - jewelSize)
        val jewelY = MathUtils.random(backGroundHeight - jewelSize)
        val jewelRadius = jewelSize / 2f
        val jewelArea = Circle(
                jewelX + jewelRadius, jewelY + jewelRadius, jewelRadius)

        return if (cannonArea.overlaps(jewelArea)) {
            jewelPosition(area, total)
        } else jewelX to jewelY
    }
}