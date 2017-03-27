package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.scenes.scene2d.Actor


class BulletsBar(val origin: Int) : Actor() {
    private val renderer = ShapeRenderer()
    private var remain = origin
    private var call: ((Int) -> Unit)? = null
    private val green = Color(50f / 256f, 205f / 256f, 50f / 256f, 1f)
    
    init {
        setBounds(11f, 6f, 265f, 4f)
    }
    
    override fun draw(batch: Batch?, parentAlpha: Float) {
        batch!!.end()
        renderer.run {
            projectionMatrix = batch.projectionMatrix
            begin(ShapeRenderer.ShapeType.Filled)
            val percent = remain / origin.toFloat()
            color = when {
                percent < 0.25f -> Color.ORANGE
                else -> green
            }
            rect(x, y, width * percent, height)
            end()
        }
        batch.begin()
    }

    fun decreaseBullets() {
        if (remain > 0) {
            remain -= 1
            this.call?.invoke(remain)
        }
    }

    fun onDecrease(call: (Int) -> Unit) {
        this.call = call
    }
}