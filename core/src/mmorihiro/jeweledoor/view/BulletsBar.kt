package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor


class BulletsBar(val origin: Int) : Actor() {
    private val renderer = ShapeRenderer()
    private var remain = origin
    
    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        renderer.run {
            projectionMatrix = batch.projectionMatrix
            begin(ShapeRenderer.ShapeType.Filled)
            val percent = remain / origin.toFloat()
            color = when {
                percent < 0.25f -> Color.ORANGE
                else -> Color.GREEN
            }
            rect(4f, 2f, 280f * percent, 16f)
            end()
        }
        batch.begin()
    }

    fun decreaseBullets() {
        remain -= 1
    }
}