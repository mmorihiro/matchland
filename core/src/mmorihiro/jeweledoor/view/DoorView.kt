package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import ktx.assets.asset


class DoorView : Stage() {
    val backGround = Image(asset<Texture>("doorBackground.png"))
    val rightDoor = Image(asset<Texture>("door.png")).apply {
        setPosition(158f, 40f)
    }
    val leftDoor = Image(asset<Texture>("door.png")).apply {
        setPosition(30f, rightDoor.y)
    }
    val jewelFrames = LoadJewels(16, 4, "jewelFrames.png")
            .loadRandom().mapIndexed { index, jewel ->
        val (x, y) = when (index) {
            0 -> 51f to 52f
            1 -> 64f to 37f
            2 -> 51f to 22f
            3 -> 39f to 37f
            else -> error("")
        }
        jewel.apply { setPosition(rightDoor.x + x - 8, rightDoor.y + y - 8) }
    }

    private var earnedJewels = listOf<Jewel>()
    private val jewelLoader = LoadJewels(16, 1, "miniJewels.png")

    fun addJewel(jewel: Jewel) {
        earnedJewels += jewelLoader.load(listOf(jewel.type)).first().apply {
            val frame = jewelFrames[jewel.number]
            setPosition(frame.x, frame.y)
            this@DoorView + this
        }
    }
}