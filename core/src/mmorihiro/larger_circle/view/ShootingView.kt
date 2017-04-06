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
    val viewSize = 288f
    var bullets: List<Image> = listOf()
        private set

    val backGround = Image(asset<Texture>("background.png"))
    val backgroundBubble =
            Image(asset<Texture>("backgroundBubble.png")).apply {
                centerPosition(viewSize, viewSize - 10)
                /* x = (viewSize - width) / 2
                 y = viewSize - height - 20*/
            }

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        centerPosition(backGround.width, viewSize)
    }

    private val cannonArea = with(cannon) {
        Circle(x + width / 2, y + height / 2, height / 2)
    }

    var bubbles: List<Bubble> = LoadBubbles(32, 4, "bubbles.png")
            .loadRandom().mapIndexed { index, bubble ->
        val (bubbleX, bubbleY) = BasicViewModel(
                32,
                backgroundBubble.width,
                backgroundBubble.height,
                cannonArea).bubblePosition(index, 4)
        bubble.apply {
            x = bubbleX + backgroundBubble.x
            y = bubbleY + backgroundBubble.y
        }
    }
        private set

    private var listeners: List<(ShootingView) -> Unit> = listOf()

    fun shoot(): Image =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(backGround.width, viewSize)
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

