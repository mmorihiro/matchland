package mmorihiro.larger_circle.model

import com.badlogic.gdx.math.Circle
import io.kotlintest.specs.ShouldSpec


class BasicViewModelSpec : ShouldSpec() {
    init {
        "bubblePosition" {
            val width = 200f
            val height = 200f
            val cannonArea = Circle(width / 2, height / 2, 32f)
            val bubbleRadius = 10
            val model =
                    BasicViewModel(bubbleRadius * 2, width, height, cannonArea)
            val bubbles = (0..3).map {
                val (vx, vy) = model.bubblePosition(it, 4)
                Circle(vx + bubbleRadius, vy + bubbleRadius,
                        bubbleRadius.toFloat())
            }
            should("大砲と被らない位置を返す") {
                bubbles.forEach {
                    cannonArea.overlaps(it) shouldBe false
                }
            }

            should("他の宝石と被らない位置を返す") {
                bubbles.forEach { circle ->
                    bubbles.filterNot { it == circle }.forEach {
                        it.overlaps(circle) shouldBe false
                    }
                }
            }
        }
    }
}