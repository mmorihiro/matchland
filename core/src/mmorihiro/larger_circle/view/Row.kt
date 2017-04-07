package mmorihiro.larger_circle.view

import com.badlogic.gdx.scenes.scene2d.Group
import ktx.actors.plus


class Row(val bubbles: List<Bubble>) : Group() {
    init {
        bubbles.forEach {
            this + it
        }
    }

    companion object {
        fun nextRow() = Row(loadBubbles.loadRandom())
    }
}
