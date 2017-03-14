package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.jeweledoor.model.BasicControllerModel
import mmorihiro.jeweledoor.view.BasicView


class BasicViewController : ViewController {
    override val view = BasicView().apply {
        backGround.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) =
                        BasicControllerModel().getBulletTarget(
                                backGround.width / 2, backGround.height / 2,
                                clickedX, clickedY)
                val actions = Actions.moveBy(vx * 260, vy * 260, 0.9f) then
                        Actions.run { removeBullet(it) }
                it + actions
                cannon + blinkCannon(darkFilter)
            }
        }
        this + backGround
        this + cannon
        jewels.forEach { this + it }
        Gdx.input.inputProcessor = this
    }

    private fun blinkCannon(originColor: Color): Action =
            Actions.color(Color.WHITE, 0.1f) then
                    Actions.delay(0.3f) then
                    Actions.color(originColor)
}