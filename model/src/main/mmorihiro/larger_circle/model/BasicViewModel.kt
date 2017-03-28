package mmorihiro.larger_circle.model

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils


class BasicViewModel(val bubbleSize: Int,
                     val backGroundWidth: Float,
                     val backGroundHeight: Float,
                     val cannonArea: Circle) {
    fun bubblePosition(area: Int,
                       total: Int): Pair<Float, Float> {
        val xRange = (backGroundWidth - bubbleSize) / total
        val bubbleX = MathUtils.random(xRange * (area % total),
                xRange * (area % total + 1) - bubbleSize)
        val bubbleY = MathUtils.random(backGroundHeight - bubbleSize)
        val bubbleRadius = bubbleSize / 2f
        val bubbleArea = Circle(
                bubbleX + bubbleRadius, bubbleY + bubbleRadius, bubbleRadius)
        val backgroundArea = Circle(
                backGroundWidth / 2, backGroundHeight / 2,
                backGroundWidth / 2 - 5f)
        return if (cannonArea.overlaps(bubbleArea)
                || !backgroundArea.contains(bubbleArea)) {
            bubblePosition(area, total)
        } else bubbleX to bubbleY
    }
}