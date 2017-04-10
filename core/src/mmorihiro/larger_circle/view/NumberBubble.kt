package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.scene2d.Scene2DSkin

class NumberBubble(region: TextureRegion,
                   type: Pair<Int, Int>,
                   private var points: List<Pair<Float, Float>>,
                   val number: Int) : Bubble(region, type) {
    val label = Label("$number", Scene2DSkin.defaultSkin).apply {
        setPosition(this@NumberBubble.x + 11, this@NumberBubble.y + 3)
    }

    init {
        nextPoint()
    }

    fun nextPoint() {
        val point = points.first()
        points = points.drop(1) + points.first()
        setPosition(point.first - width / 2, point.second - height / 2)
        label.setPosition(x + 11, y + 3)
    }
}