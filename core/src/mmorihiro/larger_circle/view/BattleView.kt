package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset


class BattleView : Stage() {
    val background = Image(asset<Texture>("upBackground.png")).apply {
        y = -20f
    }

    val ground = Image(asset<Texture>("tiles.png")).apply {
        y = -20f
    }
    //  private val bubbles = loadBubbles.loadRandom()

    val playerCannon = Image(asset<Texture>("playerCannon.png")).apply {
        setPosition(50f, 76f)
    }
/*var player = bubbles[0].let {
    it.numberBubble(1).apply {
        decorateBubble(this)
    }
}
    private set

val enemy = bubbles[1].let {
    it.numberBubble(5).apply {
        group.setPosition(197f, 55f)
    }
}

fun decorateBubble(bubble: NumberBubble) {
    bubble.run {
        setPosition(30f, 53f)
        setScale(0.5f + 0.5f * number / 5)
        val position = width / 2 * scaleX
        label.setPosition(25f + position, 40f + position)
    }
}

fun updatePlayer(new: NumberBubble) {
    decorateBubble(new)
    player.group.remove()
    player = new
    this + new.group
 */
}