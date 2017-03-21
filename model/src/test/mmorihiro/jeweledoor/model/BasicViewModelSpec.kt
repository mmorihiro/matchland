package mmorihiro.jeweledoor.model

import com.badlogic.gdx.math.Circle
import io.kotlintest.specs.ShouldSpec


class BasicViewModelSpec : ShouldSpec() {
    val width = 100f
    val height = 100f
    val cannonArea = Circle(width + 10f, height + 10f, 32f)

    init {
        "jewelPosition" {
            val jewel = circle()
            should("大砲と被らない位置を返す") {
                cannonArea.overlaps(jewel) shouldBe false
            }
        }
    }

    fun circle(): Circle {
        val jewelRadius = 10
        val model = BasicViewModel(jewelRadius * 2, width, height, cannonArea)
        val (vx, vy) = model.jewelPosition()
        return Circle(vx + jewelRadius, vy + jewelRadius,
                jewelRadius.toFloat())
    }
}