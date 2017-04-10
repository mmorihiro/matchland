package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.alpha
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.larger_circle.model.Values


class UnderWater(passedBubbles: List<Bubble>) : Group() {
    private val backShape = Image(asset<Texture>("backCircle.png")).apply {
        x = (Values.width - width) / 2
        y = (Values.width - 18 - height) / 2
        alpha = 0.7f
    }
    private val data =
            listOf(4f to 110f, 34f to 34f,
                    110f to 6f, 186f to 34f,
                    218f to 110f, 186f to 186f,
                    110f to 218f, 34f to 188f).map {
                it.first + backShape.x to it.second + backShape.y
            }

    val points = (0..data.size - 1).map {
        Image(asset<Texture>("point.png")).apply {
            x = data[it].first - width / 2
            y = data[it].second - height / 2
            alpha = 0.8f
        }
    }

    val pointGroup = (Group() + backShape).apply {
        points.forEach { this + it }
    }

    var bubbles = passedBubbles.mapIndexed { index, bubble ->
        bubble.numberBubble(2, data.drop(index * 2) + data.take(index * 2))
    }
        private set

    init {
        this + pointGroup
        bubbles.forEach {
            this + it
            this + it.label
        }
    }

    fun removeBubble(bubble: NumberBubble) {
        bubble.remove()
        bubbles -= bubble
    }
}