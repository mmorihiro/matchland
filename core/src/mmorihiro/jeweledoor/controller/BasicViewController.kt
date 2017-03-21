package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import ktx.actors.plus
import mmorihiro.jeweledoor.view.BasicView


class BasicViewController(bulletCounter: () -> Unit) : ViewController {
    override val view = BasicView().apply {
        val shoot = ShootAction(bulletCounter)
        shoot.shootAction(this)
        shoot.collision(this)

        jewel + BounceAction(this).run {
            val (vx, vy) = first()
            bounceAction(vx, vy)
        }
        
        this + backGround
        this + cannon
        this + jewel
        Gdx.input.inputProcessor = this
    }
}
