package mmorihiro.jeweledoor.model


class BasicViewModel {
    fun getBulletTarget(clickedX: Float, clickedY: Float): Pair<Float, Float> {
        val ySide = (clickedY - Values.centerY).toDouble()
        val xSide = (clickedX - Values.centerX).toDouble()
        val direction = Math.atan2(ySide, xSide)
        val vx = Math.cos(direction).toFloat()
        val vy = Math.sin(direction).toFloat()
        return (if (xSide == 0.0) 0f else vx) to (if (ySide == 0.0) 0f else vy)
    }
}

