package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset


class BarView(originBullets: Int) : Stage() {
    val backGroundBar = Image(asset<Texture>("backGroundBar.png"))
    val bar = BulletsBar(originBullets)
}