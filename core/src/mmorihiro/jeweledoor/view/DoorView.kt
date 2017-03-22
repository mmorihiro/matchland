package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset


class DoorView : Stage() {
    val backGround = Image(asset<Texture>("doorBackground.png"))
    val rightDoor = Image(asset<Texture>("door.png")).apply {
        setPosition(160f, 50f)
    }
    val leftDoor = Image(asset<Texture>("door.png")).apply {
        setPosition(44f, rightDoor.y)
    }
    val jewels = LoadJewels(32, 4, "jewelFrames.png")
            .loadRandom().mapIndexed { index, jewel ->
        val (x, y) = when (index) {
            0 -> 5f to 60f
            1 -> 45f to 60f
            2 -> 5f to 10f
            3 -> 45f to 10f
            else -> error("")
        }
        jewel.apply { setPosition(rightDoor.x + x, rightDoor.y + y) }
    }
}