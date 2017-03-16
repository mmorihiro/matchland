package mmorihiro.jeweledoor.model

import io.kotlintest.specs.ShouldSpec


class BasicControllerModelSpec : ShouldSpec() {
    init {
        "BasicControllerModel" {
            val model = BasicControllerModel()
            should("クリックされた位置を通るためのXとYの比率を返す") {
                val (vx, vy) = model.getBulletTarget(
                        Values.centerX, Values.centerY, Values.centerX, 0f)
                vx shouldBe 0f
                vy shouldBe -1f
            }
        }
    }
}