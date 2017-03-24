package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.plus
import mmorihiro.jeweledoor.view.BasicView


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
                !backGround.rectAngle().contains(currentJewel.rectAngle()) -> {
                    bounceWall(vx, vy)
                }
                cannon.circle().overlaps(currentJewel.circle()) -> {
                    currentJewel.x -= vx * 3
                    currentJewel.y -= vy * 3
                    bounce(-vx, -vy)
                }
                else -> false
            }
        }
        val moveByAction = Actions.moveBy(vx * 300, vy * 300, 1.8f)
        Actions.parallel(moveByAction, action)
    }

    private fun bounceWall(vx: Float, vy: Float): Boolean = view.run {
        val jewelRight = currentJewel.x + currentJewel.width
        val jewelUp = currentJewel.y + currentJewel.height
        val vx2 = if (jewelRight >= backGround.width || currentJewel.x <= 0) {
            if (currentJewel.x <= 0) currentJewel.x += 2
            if (jewelRight >= backGround.width) currentJewel.x -= 2
            -vx
        } else vx
        val vy2 = if (jewelUp >= backGround.height || currentJewel.y <= 0) {
            if (currentJewel.y <= 0) currentJewel.y += 2
            if (jewelUp >= backGround.height) currentJewel.y -= 2
            -vy
        } else vy
        bounce(vx2, vy2)
    }

    private fun bounce(vx: Float, vy: Float): Boolean = view.run {
        currentJewel.clearActions()
        currentJewel + bounceAction(vx, vy)
        true
    }
}