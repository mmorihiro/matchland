package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.CircularMotion
import mmorihiro.larger_circle.view.BasicView


class BasicViewController(bulletCounter: () -> Unit) : ViewController {
    override val view = BasicView().apply {
        val shoot = ShootAction(bulletCounter)
        shoot.shootAction(this)
        shoot.collision(this)
        bubbles.forEach {
            val move = CircularMotion(144f, 144f, 6, it)
            it + forever(fadeIn(0.6f) then fadeOut(0.2f) then
                    delay(0.8f) then Actions.run {
                val (nextX, nextY) = move.next()
                it.setPosition(nextX, nextY)
            })
        }
        this + backGround
        this + backgroundBubble
        this + cannon
        bubbles.forEach { this + it }
        Gdx.input.inputProcessor = this
    }
}
