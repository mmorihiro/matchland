package mmorihiro.larger_circle.model

import com.badlogic.gdx.scenes.scene2d.Actor
import io.kotlintest.specs.ShouldSpec


class CircularMotionSpec : ShouldSpec() {
    init {
        "next" {
            val actor = Actor().apply {
                x = 15f
                y = 25f
                width = 10f
                height = 10f
            }
            val motion = CircularMotion(20f, 20f, 4, actor)
            should("アクターの次の円周上の位置を返す") {
                val (x, y) = motion.next()
                x shouldBe 5f
                y shouldBe 15f
            }
        }
    }
}