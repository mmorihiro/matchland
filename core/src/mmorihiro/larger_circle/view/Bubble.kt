package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.ui.Image


open class Bubble(val region: TextureRegion,
                  val type: Pair<Int, Int>) : Image(region) {
    init {
        region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }

    var isDamaged = false
        private set

    fun damage() {
        assert(!isDamaged)
        isDamaged = true
    }

    fun numberBubble(number: Int, points: List<Pair<Float, Float>>) =
            NumberBubble(region, type, points, number)

    fun circle(): Circle = Circle(
            x + imageWidth / 2, y + imageWidth / 2, imageWidth / 2)
}