package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.plus
import mmorihiro.larger_circle.view.BasicView


class BounceAction(val view: BasicView) {
    fun first(): Pair<Float, Float> =
            when (MathUtils.random(3)) {
                0 -> 1f to 1f
                1 -> -1f to 1f
                2 -> 1f to -1f
                3 -> -1f to -1f
                else -> error("")
            }
    
    fun bounceAction(vx: Float, vy: Float): Action = view.run {
        val action = action {
            when {
                !backGround.rectAngle().contains(jewel.rectAngle()) -> {
                    bounceWall(vx, vy)
                }
                cannon.circle().overlaps(jewel.circle()) -> {
                    jewel.x -= vx * 3
                    jewel.y -= vy * 3
                    bounce(-vx, -vy)
                }
                else -> false
            }
        }
        val moveByAction = Actions.moveBy(vx * 300, vy * 300, 1.8f)
        Actions.parallel(moveByAction, action)
    }

    private fun bounceWall(vx: Float, vy: Float): Boolean = view.run {
        val jewelRight = jewel.x + jewel.width
        val jewelUp = jewel.y + jewel.height
        val vx2 = if (jewelRight >= backGround.width || jewel.x <= 0) {
            if (jewel.x <= 0) jewel.x += 2
            if (jewelRight >= backGround.width) jewel.x -= 2
            -vx
        } else vx
        val vy2 = if (jewelUp >= backGround.height || jewel.y <= 0) {
            if (jewel.y <= 0) jewel.y += 2
            if (jewelUp >= backGround.height) jewel.y -= 2
            -vy
        } else vy
        bounce(vx2, vy2)
    }

    private fun bounce(vx: Float, vy: Float): Boolean = view.run {
        jewel.clearActions()
        jewel + bounceAction(vx, vy)
        true
    }
}