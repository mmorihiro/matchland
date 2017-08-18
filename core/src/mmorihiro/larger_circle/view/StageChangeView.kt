package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.alpha
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.asset
import mmorihiro.larger_circle.model.Values


class StageChangeView(onFinish: () -> Unit) : View() {
    val label = Image(asset<Texture>("clear.png")).apply {
        centerPosition(Values.width, Values.height)
        y -= 15
        alpha = 0f
        this + (parallel(
                fadeIn(0.5f),
                moveBy(0f, 15f, 0.5f))
                then delay(1f)
                then Actions.run { onFinish() })
    }
}