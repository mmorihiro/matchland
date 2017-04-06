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
        backgroundBubble.onClick { _, _, clickedX, clickedY ->
            shoot().let {
                val (vx, vy) =
                        BasicControllerModel().getBulletTarget(
                                backGround.width / 2, viewSize / 2,
                                clickedX, clickedY)
                val actions = Actions.moveBy(vx * 132, vy * 132, 0.5f) then
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