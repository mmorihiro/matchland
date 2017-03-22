package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.jeweledoor.model.BasicViewModel

class BasicView(private var jewels: List<Image>) : Stage() {
    var bullets: List<Image> = listOf()
        get private set

    val darkFilter = Color(0.7f, 0.7f, 0.7f, 1f)

    val backGround = Image(asset<Texture>("background.png"))

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        centerPosition(backGround.width, backGround.height)
        color = darkFilter
    }

    var currentJewel = newJewel(jewels.first())
        get private set

    private var listeners: List<(BasicView) -> Unit> = listOf()

    private fun newJewel(jewel: Image): Image {
        val cannonArea = with(cannon) {
            Circle(x + width / 2, y + height / 2, width / 2)
        }
        val (jewelX, jewelY) = BasicViewModel(
                32, backGround.width, backGround.height, cannonArea)
                .jewelPosition()
        return jewel.apply {
            setPosition(jewelX, jewelY)
        }
    }

    fun shoot() =
            Image(asset<Texture>("bullet.png")).apply {
                bullets += this
                centerPosition(backGround.width, backGround.height)
                this@BasicView + this
            }

    fun removeBullet(bullet: Image) {
        bullet.remove()
        bullets -= bullet
    }

    fun removeJewel() {
        currentJewel.remove()
        if (jewels.size == 1) {
            println("complete")
        } else {
            jewels = jewels.drop(1)
            currentJewel = newJewel(jewels.first())
            this + currentJewel
        }
    }

    fun addListener(listener: (BasicView) -> Unit) {
        listeners += listener
    }

    override fun act(delta: Float) {
        super.act(delta)
        listeners.forEach {
            it(this)
        }
    }
}
