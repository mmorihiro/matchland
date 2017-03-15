package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset

class BasicView : Stage() {
    private var bullets: List<Image> = listOf()
    val darkFilter = Color(0.7f, 0.7f, 0.7f, 1f)

    val backGround = Image(asset<Texture>("background.png"))

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        centerPosition(backGround.width, backGround.height)
        color = darkFilter
    }

    fun shoot(): Image =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(backGround.width, backGround.height)
                this@BasicView + this
            }

    fun removeBullet(bullet: Image) {
        bullet.remove()
        bullets -= bullet
    }
}

