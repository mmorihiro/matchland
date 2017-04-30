package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.alpha
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin

class RecordView(star: Int) : View() {
    val backGround = Image(asset<Texture>("recordBack.png"))
    val group = Group()
    val window = Image(asset<Texture>("window.png"))

    val starBar = Image(asset<Texture>("starBar.png")).apply {
        x = 12f
        y = 190f
    }

    val starLabel = Label("$star", Scene2DSkin.defaultSkin).apply {
        x = starBar.x + 38f + if (star < 10) 3f else 0f
        y = starBar.y + 3f
        color = Color(255f / 255f, 204f / 255f, 0f, 0.7f)
    }

    val frames = (0..4).map {
        Image(asset<Texture>("starFrame.png")).apply {
            x = 12f + 45f * it
            y = 130f
        }
    }

    val stars = (0..(when {
        star <= 15 -> 0
        star / 15 <= 4 -> star / 15
        else -> 4
    })).map {
        Image(asset<Texture>("recordStar.png")).apply {
            x = 12f + 45f * it
            y = 130f
            alpha = 0f
        }
    }
}