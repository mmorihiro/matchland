package mmorihiro.larger_circle.view

import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.actors.plus


class BattleView : Stage() {
    private val bubbles = loadBubbles.loadRandom()
    var player = bubbles[0].let {
        it.numberBubble(1).apply {
            decorateBubble(this)
        }
    }
        private set

    val enemy = bubbles[1].let {
        it.numberBubble(5).apply {
            group.setPosition(180f, 30f)
        }
    }

    fun decorateBubble(bubble: NumberBubble) {
        bubble.run {
            setPosition(40f, 28f)
            setScale(0.5f + 0.5f * number / 5)
            val position = width / 2 * scaleX
            label.setPosition(35f + position, 18f + position)
        }
    }

    fun updatePlayer(new: NumberBubble) {
        decorateBubble(new)
        player.group.remove()
        player = new
        this + new.group
    }
}