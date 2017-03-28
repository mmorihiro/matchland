package mmorihiro.larger_circle.model

import com.badlogic.gdx.math.Circle
import io.kotlintest.specs.ShouldSpec


class BasicViewModelSpec : ShouldSpec() {
    init {
        "bubblePosition" {
            val (cannonArea, circles) = circles()
            should("大砲と被らない位置を返す") {
                circles.forEach {
                    cannonArea.overlaps(it) shouldBe false
                }
            }

            should("他の宝石と被らない位置を返す") {
                circles.forEach { circle ->
                    circles.filterNot { it == circle }.forEach {
                        it.overlaps(circle) shouldBe false
                    }
                }
            }
        }
    }

    private fun circles(): Pair<Circle, List<Circle>> {
        val width = 100f
        val height = 100f
        val cannonArea = Circle(width + 10f, height + 10f, 32f)
        val jewelRadius = 10
        val model = BasicViewModel(jewelRadius * 2, width, height, cannonArea)
        val circles = (0..3).map {
            val (vx, vy) = model.bubblePosition(it, 4)
            cannonArea.contains(vx, vy) shouldBe false
            Circle(vx + jewelRadius, vy + jewelRadius,
                    jewelRadius.toFloat())
        }
        return Pair(cannonArea, circles)
    }
}