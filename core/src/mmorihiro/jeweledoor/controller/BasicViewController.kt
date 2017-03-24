package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import ktx.actors.plus
import mmorihiro.jeweledoor.view.BasicView
import mmorihiro.jeweledoor.view.Jewel


class BasicViewController(bulletCounter: () -> Unit,
                          positions: List<Pair<Int, Int>>,
                          onHit: (jewel: Jewel) -> Unit) : ViewController {
    override val view = BasicView(positions).apply {
        val shoot = ShootAction(bulletCounter, onHit)
        shoot.shootAction(this)
        shoot.collision(this)

        currentJewel + BounceAction(this).run {
            val (vx, vy) = first()
            bounceAction(vx, vy)
        }
        
        this + backGround
        this + cannon
        this + currentJewel
        Gdx.input.inputProcessor = this
    }
}
