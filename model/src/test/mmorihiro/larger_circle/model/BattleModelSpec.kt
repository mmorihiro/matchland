package mmorihiro.larger_circle.model

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec


class BattleModelSpec : ShouldSpec() {
    init {
        "decideReaction" {
            should("相性を判定する") {
                val model = BattleModel()
                val result = model.decideReaction(
                        BubbleType.BLUE.position,
                        BubbleType.RED.position)
                result shouldBe Reaction.WIN
            }
        }
    }
}