package mmorihiro.jeweledoor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset

class View : Stage(FitViewport(Values.width, Values.height)) {
    private var bullets: List<Image> = listOf()

    val backGround = object : Actor() {
        override fun act(delta: Float) = stage.viewport.run {
            width = screenWidth.toFloat()
            height = screenHeight.toFloat()
            x = screenX.toFloat()
            y = screenY.toFloat()
        }
    }

    fun shoot(): Image =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(Values.width, Values.height)
                this@View + this
            }

    fun removeBullet(bullet: Image) {
        bullet.remove()
        bullets -= bullet
    }

}

