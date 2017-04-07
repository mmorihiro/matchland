package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.CircularMotion
import mmorihiro.larger_circle.view.ShootingView


class ShootingViewController(onShoot: () -> Unit,
                             onHit: (Pair<Int, Int>) -> Unit) : ViewController {
    override val view = ShootingView().apply {
        val shoot = ShootAction(onShoot, onHit)
        shoot.shootAction(this)
        shoot.collision(this)
        bubbles.forEach {
            val move = CircularMotion(viewWidth / 2, viewHeight / 2, 6, it)
            it + forever(fadeIn(0.3f) then delay(0.8f) then fadeOut(0.3f) then
                    delay(0.8f) then Actions.run {
                val (nextX, nextY) = move.next()
                it.setPosition(nextX, nextY)
            })
        }
        addListener {
            bullets.filter { it.y + it.height > viewHeight }.forEach {
                removeBullet(it)
            }
        }
        this + backGround
        bubbles.forEach { this + it }
        Gdx.input.inputProcessor = this
    }
}
