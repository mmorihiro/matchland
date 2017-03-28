package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.plus
import mmorihiro.larger_circle.model.CircularMotion
import mmorihiro.larger_circle.view.BasicView


class BasicViewController(bulletCounter: () -> Unit) : ViewController {
    override val view = BasicView().apply {
        val shoot = ShootAction(bulletCounter)
        shoot.shootAction(this)
        shoot.collision(this)
        bubbles.forEach {
            it + Actions.forever(CircularMotion(144f, 144f, 2.8f))
        }
        this + backGround
        this + backgroundBubble
        this + cannon
        bubbles.forEach { this + it }
        Gdx.input.inputProcessor = this
    }
}
