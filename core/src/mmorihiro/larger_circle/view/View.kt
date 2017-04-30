package mmorihiro.larger_circle.view

import com.badlogic.gdx.scenes.scene2d.Stage


open class View : Stage() {
    private var listeners: List<(View) -> Boolean> = listOf()
    var pause = false

    fun onAct(listener: (View) -> Boolean) {
        listeners += listener
    }

    fun onEveryAct(listener: (View) -> Unit) {
        listeners += {
            listener(this)
            false
        }
    }

    override fun act(delta: Float) {
        if (!pause) super.act(delta)
        listeners.filter { it(this) }.forEach { listeners -= it }
    }
}