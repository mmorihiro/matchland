package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.BasicControllerModel
import mmorihiro.larger_circle.view.ShootingView


class ShootAction(val bulletCounter: () -> Unit,
                  val onHit: (Pair<Int, Int>) -> Unit) {
    fun shootAction(view: ShootingView) = view.run {
        backgroundBubble.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) =
                        BasicControllerModel().getBulletTarget(
                                backGround.width / 2, viewHeight / 2,
                                clickedX, clickedY)
                val actions = Actions.moveBy(vx * 132, vy * 132, 0.5f) then
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

    fun collision(view: ShootingView) = view.run {
        addListener {
            bullets.forEach { bullet ->
                bubbles.filter { jewel ->
                    bullet.circle().overlaps(jewel.circle())
                }.forEach {
                    removeBubble(it)
                    removeBullet(bullet)
                    onHit(it.type)
                }
            }
        }
    }
}