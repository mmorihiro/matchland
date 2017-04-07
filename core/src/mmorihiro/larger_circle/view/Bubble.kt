package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.scene2d.Scene2DSkin


open class Bubble(val region: TextureRegion,
                  val type: Pair<Int, Int>) : Image(region) {
    fun numberBubble(number: Int): NumberBubble =
            NumberBubble(region, type, number)
}

class NumberBubble(region: TextureRegion,
                   type: Pair<Int, Int>,
                   val number: Int) : Bubble(region, type) {
    init {
        region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }

    val label = Label("$number", Scene2DSkin.defaultSkin).apply {
        centerPosition(this@NumberBubble.width, this@NumberBubble.height)
    }
    val group = Group() + this + label

    fun newBubble(type: Pair<Int, Int>) =
            loadBubbles.load(type).numberBubble(number + 1)
}

val loadBubbles = LoadBubbles(32, 3, "bubbles.png")