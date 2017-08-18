package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.larger_circle.model.Values


class BarView : View() {
    val bar = Image(asset<Texture>("bar.png")).apply {
        x = (Values.width - width) / 2
        y = 14f
        color = Colors.fire
    }
    val barWidth = bar.width

    fun getPercentWidth(value: Int): Float = barWidth * value / 35f
}