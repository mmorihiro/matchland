package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.alpha
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.view.RecordView


class RecordController(star: Int, stop: () -> Unit) : Controller {
    override val view = RecordView(star).apply {
        backGround.alpha = 0f
        backGround + (Actions.delay(0.2f) then Actions.run {
            stop()
        } then Actions.fadeIn(0.2f) then Actions.run {
            group.x = 20f
            group.y = 70 + 288f
            this + group
            group + window
            group + starBar
            group + starLabel
            frames.forEach { group + it }
            stars.forEach { group + it }
            group + Actions.moveBy(0f, -288f, 0.2f)
            window + (Actions.delay(0.3f) then Actions.run {
                stars.forEachIndexed { index, image ->
                    image + (Actions.delay(0.5f + index * 0.5f)
                            then Actions.fadeIn(0.4f))
                }
            })
        })
        this + backGround
    }
}