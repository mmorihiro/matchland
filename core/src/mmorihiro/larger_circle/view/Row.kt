package mmorihiro.larger_circle.view

import com.badlogic.gdx.scenes.scene2d.Group
import ktx.actors.plus


class Row(passedBubbles: List<Bubble>) : Group() {
    init {
        passedBubbles.forEach { this + it }
    }

    companion object {
        fun nextRow() = Row(loadBubbles.loadRandom())
    }

    var bubbles = passedBubbles
        private set

    fun removeBubble(bubble: Bubble) {
        bubble.remove()
        bubbles -= bubble
    }
}
