package mmorihiro.larger_circle.model


class BasicControllerModel {
    fun getBulletTarget(startX: Float, startY: Float,
                        clickedX: Float, clickedY: Float): Pair<Float, Float> {
        val ySide = (clickedY - startY).toDouble()
        val xSide = (clickedX - startX).toDouble()
        val degree = Math.atan2(ySide, xSide)
        val vx = Math.cos(degree).toFloat()
        val vy = Math.sin(degree).toFloat()
        return (if (xSide == 0.0) 0f else vx) to (if (ySide == 0.0) 0f else vy)
    }
}

