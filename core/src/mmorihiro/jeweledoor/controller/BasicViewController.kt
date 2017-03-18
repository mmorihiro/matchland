package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.MathUtils
import ktx.actors.plus
import mmorihiro.jeweledoor.view.BasicView


class BasicViewController : ViewController {
    override val view = BasicView().apply {
        val shoot = ShootAction()
        shoot.shootAction(this)
        shoot.collision(this)
        jewels.forEach { jewel ->
            val (vx, vy) = when (MathUtils.random(3)) {
                0 -> 1f to 1f
                1 -> -1f to 1f
                2 -> 1f to -1f
                3 -> -1f to -1f
                else -> error("")
            }
            jewel + BounceAction(this, jewel).bounceAction(vx, vy)
        }
        this + backGround
        this + cannon
        jewels.forEach { this + it }
        Gdx.input.inputProcessor = this
    }
}
