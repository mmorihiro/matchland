package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor


class BulletsBar(val origin: Int) : Actor() {
    private val renderer = ShapeRenderer()
    private var remain = origin.toFloat()
    
    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        if (remain > 0) {
            remain -= 0.1f
        }
        renderer.run {
            projectionMatrix = batch.projectionMatrix
            begin(ShapeRenderer.ShapeType.Filled)
            val percent = remain / origin
            color = when {
                percent < 0.25f -> Color.ORANGE
                else -> Color.GREEN
            }
            rect(4f, 2f, 280f * remain / origin, 16f)
            end()
        }
        batch.begin()
    }
}