package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import mmorihiro.jeweledoor.view.BasicView


class BasicViewController(bulletCounter: () -> Unit,
                          val jewels: List<Image>) : ViewController {
    override val view = BasicView(jewels).apply {
        val shoot = ShootAction(bulletCounter)
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
