package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import mmorihiro.larger_circle.model.Values


open class View(val vp: Viewport = FitViewport(Values.width, Values.height, OrthographicCamera())) : Stage(vp) {
    private var listeners: List<(View) -> Boolean> = listOf()
    var pause = false

    override fun act(delta: Float) {
        if (!pause) super.act(delta)
        listeners.filter { it(this) }.forEach { listeners -= it }
    }

    fun screenPosToWorldPos(x: Int, y: Int) = run {
        val worldPos = vp.unproject(Vector2(x.toFloat(), y.toFloat()))
        Pair(worldPos.x.toInt(), worldPos.y.toInt())
    }
}
