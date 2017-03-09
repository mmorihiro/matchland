package mmorihiro.jeweledoor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.actors.plus
import ktx.assets.asset

class View : Stage(FitViewport(width, height)) {
    private var bullets: List<Image> = listOf()

    val backGround = object : Actor() {
        override fun act(delta: Float) = stage.viewport.run {
            width = screenWidth.toFloat()
            height = screenHeight.toFloat()
            x = screenX.toFloat()
            y = screenY.toFloat()
        }
    }

    fun shoot() {
        bullets += Image(asset<Texture>("bullet.png")).apply {
            setPosition(100f, 100f)
            this@View + this
        }
    }
}


