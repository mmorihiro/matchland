package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.larger_circle.model.BasicViewModel

class ShootingView : Stage() {
    val viewHeight = 288f
    var bullets: List<Image> = listOf()
        private set

    val darkFilter = Color(0.7f, 0.7f, 0.7f, 1f)

    val backGround = Image(asset<Texture>("background.png"))
    val backgroundBubble =
            Image(asset<Texture>("backgroundBubble.png")).apply {
                setPosition(4f, 4f)
            }

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        centerPosition(backGround.width, viewHeight)
        color = darkFilter
    }
    private val cannonArea = with(cannon) {
        Circle(x + width / 2, y + height / 2, width / 2)
    }

    var bubbles: List<Bubble> = LoadBubbles(32, 4, "bubbles.png")
            .loadRandom().mapIndexed { index, bubble ->
        val (bubbleX, bubbleY) = BasicViewModel(
                32,
                backGround.width,
                viewHeight,
                cannonArea).bubblePosition(index, 4)
        bubble.apply {
            setPosition(bubbleX, bubbleY)
        }
    }
        private set

    private var listeners: List<(ShootingView) -> Unit> = listOf()

    fun shoot(): Image =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(backGround.width, viewHeight)
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

