package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin

class RecordView(stars: Int) : View() {
    val backGround = Image(asset<Texture>("recordBack.png"))
    val group = Group()
    val window = Image(asset<Texture>("window.png"))

    val starBar = Image(asset<Texture>("starBar.png")).apply {
        x = 20f
        y = 200f
    }

    val starLabel = Label("$stars", Scene2DSkin.defaultSkin).apply {
        x = starBar.x + 41f
        y = starBar.y + 3f
        color = Color(255f / 255f, 204f / 255f, 0f, 0.7f)
    }
}