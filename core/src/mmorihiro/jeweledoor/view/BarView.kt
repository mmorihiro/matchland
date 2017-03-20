package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin


class BarView(originBullets: Int) : Stage() {
    val backGroundBar = Image(asset<Texture>("backGroundBar.png")).apply {
        x = 0f
        y = 0f
    }
    val bar = BulletsBar(originBullets)

    var percentLabel = Label("bullets  ${bar.origin}/${bar.origin}",
            Scene2DSkin.defaultSkin).apply {
        x = bar.x + bar.width - 75
        y = 7f
        fontScaleX = 0.8f
        fontScaleY = 0.8f
        color = Color.WHITE
    }
}