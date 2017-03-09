package mmorihiro.jeweledoor.model

import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object BasicViewModelSpec : Spek({
    describe("a BasicViewModel") {
        val model = BasicViewModel()
        on("getBulletTarget") {
            val (vx, vy) = model.getBulletTarget(Values.centerX, 0f)
            it("クリックされた位置を通るためのXとYの比率を返すべき") {
                vx shouldEqual 0f
                vy shouldEqual -1f
            }
        }
    }
})