package mmorihiro.matchland.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.alpha
import ktx.actors.onClick
import ktx.actors.plus
import mmorihiro.matchland.view.StageChangeView


class StageChangeController(title: String, onFinish: () -> Unit) : Controller {
    override val view = StageChangeView(title).apply {
        this + window
        window.y -= 15f
        window.alpha = 0f
        window + parallel(fadeIn(0.3f), moveBy(0f, 15f, 0.3f))
        button.onClick { _, _ -> onFinish() }
    }
}