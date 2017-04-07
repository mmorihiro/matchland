package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.larger_circle.model.BasicViewModel

class ShootingView : Stage() {
    val viewWidth = 288f
    val viewHeight = viewWidth - 18
    var bullets: List<Image> = listOf()
        private set

    val backGround = Image(asset<Texture>("background.png"))

    private val cannonArea = Circle(viewWidth / 2, viewHeight / 2, 30f)

    var bubbles: List<Bubble> = LoadBubbles(32, 4, "bubbles.png")
            .loadRandom().mapIndexed { index, bubble ->
        val (bubbleX, bubbleY) = BasicViewModel(
                32, viewWidth, viewHeight, cannonArea).bubblePosition(index, 4)
        bubble.apply {
            setPosition(bubbleX, bubbleY)
        }
    }
        private set

    private var listeners: List<(ShootingView) -> Unit> = listOf()

    fun shoot(): Image =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(viewWidth, viewHeight)
                this@ShootingView + this
            }

    fun removeBullet(bullet: Image) {
        bullet.remove()
        bullets -= bullet
    }

    fun removeBubble(bubble: Bubble) {
        bubble.remove()
        bubbles -= bubble
    }


    fun addListener(listener: (ShootingView) -> Unit) {
        listeners += listener
    }

    override fun act(delta: Float) {
        super.act(delta)
        listeners.forEach {
            it(this)
        }
    }
}

