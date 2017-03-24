package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.jeweledoor.model.BasicControllerModel
import mmorihiro.jeweledoor.view.BasicView
import mmorihiro.jeweledoor.view.Jewel


class ShootAction(val bulletCounter: () -> Unit,
                  val onHit: (jewel: Jewel) -> Unit) {
    fun shootAction(view: BasicView) = view.run {
        backGround.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) =
                        BasicControllerModel().getBulletTarget(
                                backGround.width / 2, backGround.height / 2,
                                clickedX, clickedY)
                val actions = Actions.moveBy(vx * 230, vy * 230, 0.9f) then
                        Actions.run { removeBullet(it) }
                it + actions
                cannon + blinkCannon(darkFilter)
                bulletCounter()
            }
        }
    }

    private fun blinkCannon(originColor: Color): Action =
            Actions.color(Color.WHITE, 0.1f) then
                    Actions.delay(0.1f) then
                    Actions.color(originColor, 0.1f)

    fun collision(view: BasicView) = view.run {
        addListener {
            bullets.forEach { bullet ->
                if (bullet.circle().overlaps(currentJewel.circle())) {
                    onHit(currentJewel)
                    removeJewel()
                    currentJewel + BounceAction(this).run {
                        val (vx, vy) = first()
                        bounceAction(vx, vy)
                    }
                    removeBullet(bullet)
                }
            }
        }
    }
}