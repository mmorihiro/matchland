package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.centerPosition
import ktx.scene2d.Scene2DSkin


class BarView(originBullets: Int) : Stage() {
    val bar = BulletsBar(originBullets)

    val percentLabel = Label("$originBullets", Scene2DSkin.defaultSkin).apply {
        centerPosition(288f, 288f)
        fontScaleX = 0.8f
        fontScaleY = 0.8f
        color = Color.WHITE
    }
}