package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.alpha
import ktx.actors.onClick
import ktx.actors.plus
import mmorihiro.larger_circle.view.StageChangeView


class StageChangeController(onFinish: () -> Unit) : Controller {
    override val view = StageChangeView().apply {
        this + window
        window.y -= 15f
        window.alpha = 0f
        window + parallel(fadeIn(0.3f), moveBy(0f, 15f, 0.3f))
        button.onClick { _, _ -> onFinish() }
    }
}