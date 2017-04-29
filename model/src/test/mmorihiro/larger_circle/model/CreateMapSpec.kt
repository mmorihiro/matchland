package mmorihiro.larger_circle.model

import io.kotlintest.matchers.shouldNotBe
import io.kotlintest.specs.ShouldSpec


class CreateMapSpec : ShouldSpec() {
    init {
        "nextMap" {
            should("ランダムなマップを返す") {
                (0..6).forEach { num ->
                    nextMap(num).first[0][num] shouldNotBe TileType.Space
                }
            }
        }
    }

    fun nextMap(y: Int) =
            CreateMap().nextMap(List(7, { List(7, { TileType.Space }) }), y to 0)
}