package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.MathUtils.random
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.larger_circle.model.BasicViewModel

class BasicView : Stage() {
    var bullets: List<Image> = listOf()
        get private set

    val darkFilter = Color(0.7f, 0.7f, 0.7f, 1f)

    val backGround = Image(asset<Texture>("background.png"))

    val cannon = Image(asset<Texture>("cannon.png")).apply {
        centerPosition(backGround.width, backGround.height)
        color = darkFilter
    }

    var jewel = createJewel()
        get private set

    private var listeners: List<(BasicView) -> Unit> = listOf()

    private fun createJewel(): Image {
        val sheet = asset<Texture>("jewels.png")
        val jewelSize = 32
        val row = sheet.width / jewelSize
        val col = sheet.height / jewelSize
        val tiles = TextureRegion.split(sheet, jewelSize, jewelSize)
        val cannonArea = with(cannon) {
            Circle(x + width / 2, y + height / 2, width / 2)
        }
        val (jewelX, jewelY) = BasicViewModel(
                jewelSize,
                backGround.width,
                backGround.height,
                cannonArea).jewelPosition()
        return Image(tiles[random(col - 1)][random(row - 1)]).apply {
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
        jewel.remove()
        this.jewel = createJewel()
        this + jewel
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

