package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.larger_circle.model.Values


class BattleView : View() {
    val background = Image(asset<Texture>("upBackground.png")).apply {
        y = -20f
    }

    val ground = Image(asset<Texture>("tiles.png")).apply {
        y = -10f
    }

    val rightGround = Image(asset<Texture>("rightGround.png")).apply {
        setPosition(Values.width - 32, ground.y)
    }

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        setPosition(50f, ground.y + 96f)
    }

    val rows = (0..3).map {
        Row.nextRow().apply {
            bubbles.mapIndexed { index, bubble ->
                bubble.apply {
                    x = 122 + (width + 6) * index
                    y = (it - 1) * 38f + 10
                }
            }
        }
    }

    val ropes = (0..2).map {
        Image(asset<Texture>("rope.png")).apply {
            x = 96f
            y = it * 38 + 4.5f
        }
    }
}