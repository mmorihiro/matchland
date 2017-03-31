package mmorihiro.larger_circle.view

import com.badlogic.gdx.scenes.scene2d.Stage


class BattleView : Stage() {
    private val bubbles = LoadBubbles(64, 2, "largerBubbles.png").loadRandom()
    val player = bubbles[0].apply {
        setPosition(20f, 20f)
    }
    val enemy = bubbles[1].apply {
        setPosition(200f, 120f)
    }
}