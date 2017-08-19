package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.Values


class BarView : View() {
    val bar = Image(asset<Texture>("bar.png")).apply {
        x = (Values.width - width) / 2
        y = 16f
        color = Colors.fire
    }
    private val barWidth = bar.width
    var stars = listOf(50, 100, 140).map {
        getStar(StarType.GRAY).apply {
            x = it.toFloat() + bar.x
            y = 9f
        }
    }

    fun getStar(type: StarType) =
            MyImage(Image(asset<Texture>(type.fileName)), type.position)

    fun getPercentWidth(value: Int): Float = barWidth * value / 35f
}

enum class StarType {
    GRAY {
        override val position = 0 to 0
        override val fileName = "grayStar.png"
    },
    GET {
        override val position = 0 to 1
        override val fileName = "star.png"
    };

    abstract val position: Point
    abstract val fileName: String
}