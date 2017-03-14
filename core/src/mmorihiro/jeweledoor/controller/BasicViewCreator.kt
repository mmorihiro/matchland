package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.jeweledoor.model.BasicViewModel
import mmorihiro.jeweledoor.view.BasicView


class BasicViewCreator {
    val view = BasicView().apply {
        backGround.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) =
                        BasicViewModel().getBulletTarget(clickedX, clickedY)
                val actions =
                        Actions.moveBy(vx * 280, vy * 280, 0.9f) then
                                Actions.run { removeBullet(it) }
                it + actions
            }
        }
        this + backGround
        Gdx.input.inputProcessor = this
    }
}