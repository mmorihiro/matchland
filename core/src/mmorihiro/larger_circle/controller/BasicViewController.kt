package mmorihiro.larger_circle.controller

import com.badlogic.gdx.Gdx
import ktx.actors.plus
import mmorihiro.larger_circle.view.BasicView


class BasicViewController(bulletCounter: () -> Unit) : ViewController {
    override val view = BasicView().apply {
        val shoot = ShootAction(bulletCounter)
        shoot.shootAction(this)
        shoot.collision(this)
        this + backGround
        this + backgroundBubble
        this + cannon
        bubbles.forEach { this + it }
        Gdx.input.inputProcessor = this
    }
}
