package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.scene2d.Scene2DSkin


class BarView(originBullets: Int) : Stage() {
    val bar = BulletsBar(originBullets)

    val percentLabel = Label("$originBullets", Scene2DSkin.defaultSkin).apply {
        x = 10f
        color = Color.WHITE
    }
}