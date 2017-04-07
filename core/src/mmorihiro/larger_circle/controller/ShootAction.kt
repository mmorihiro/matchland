package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.BasicControllerModel
import mmorihiro.larger_circle.view.ShootingView


class ShootAction(val onShoot: () -> Unit,
                  val onHit: (Pair<Int, Int>) -> Unit,
                  val onComplete: () -> Unit) {
    fun shootAction(view: ShootingView) = view.run {
        backGround.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) = BasicControllerModel().getBulletTarget(
                        viewWidth / 2, viewHeight / 2, clickedX, clickedY)
                val actions = Actions.moveBy(vx * 200, vy * 200, 0.7f) then
                        Actions.run { removeBullet(it) }
                it + actions
                onShoot()
            }
        }
    }

    fun collision(view: ShootingView) = view.run {
        addListener {
            bullets.forEach { bullet ->
                bubbles.filter { jewel ->
                    bullet.circle().overlaps(jewel.circle())
                }.forEach {
                    removeBubble(it)
                    removeBullet(bullet)
                    onHit(it.type)
                    if (bubbles.isEmpty()) onComplete()
                }
            }
        }
    }
}