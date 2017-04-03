package mmorihiro.larger_circle.model

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec


class BattleModelSpec : ShouldSpec() {
    init {
        "decideWinner" {
            should("相性を含めて判定する") {
                val model = BattleModel()
                val result = model.decideWinner(
                        5, BubbleType.BLUE.position,
                        5, BubbleType.RED.position)
                result shouldBe true
            }
        }
    }
}