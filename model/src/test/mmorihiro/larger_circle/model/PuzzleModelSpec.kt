package mmorihiro.larger_circle.model

import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.ShouldSpec


class PuzzleModelSpec : ShouldSpec() {
    init {
        "PuzzleModel" {
            val model = PuzzleModel()
            should("周囲のバブルを返す") {
                val groups = model.getAround(
                        0 to 0, listOf(0 to 1, 1 to 0, 2 to 0))
                groups shouldBe setOf(0 to 1, 1 to 0)
            }
            
            should("同じタイプのバブルのグループを返す") {
                val groups = model.sameTypeGroup(
                        listOf(0 to 0, 0 to 1, 4 to 4, 5 to 5, 2 to 0, 2 to 1))
                groups shouldBe
                        setOf(setOf(0 to 0, 0 to 1),
                                setOf(2 to 0, 2 to 1), setOf(4 to 4, 5 to 5))
            }
        }
    }
}