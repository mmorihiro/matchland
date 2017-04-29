package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin


class BarView(private var turns: Int) : Stage() {
    val life = Image(asset<Texture>("lifeBar.png")).apply {
        x = 4f
        y = -5f
    }
    val turnLabel = Label("$turns", Scene2DSkin.defaultSkin).apply {
        x = life.x + 37f
        y = -2f
        color = Color(200f / 255f, 62f / 255f, 62f / 255f, 0.8f)
    }
    private var stars = 0
    val star = Image(asset<Texture>("starBar.png")).apply {
        x = life.x + life.width + 4f
        y = -5f
    }

    val starLabel = Label("$stars", Scene2DSkin.defaultSkin).apply {
        x = star.x + 41f
        y = -2f
        color = Color(255f / 255f, 204f / 255f, 0f, 0.7f)
    }

    fun onTurnEnd() {
        turnLabel + (Actions.fadeOut(0.2f) then Actions.run {
            turns -= 1
            if (turns == 9) {
                turnLabel.x += 5
            }
            turnLabel.setText("$turns")
        } then Actions.alpha(0.8f, 0.2f))
    }

    fun onGet() {
        starLabel + (Actions.fadeOut(0.2f) then Actions.run {
            stars += 1
            if (stars == 10) {
                starLabel.x -= 5
            }
            starLabel.setText("$stars")
        } then Actions.alpha(0.7f, 0.2f))
    }
}