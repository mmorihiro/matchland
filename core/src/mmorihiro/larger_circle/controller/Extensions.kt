package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.Action
import com.badlogic.gdx.scenes.scene2d.ui.Image


fun Image.circle(): Circle = Circle(x + width / 2, y + height / 2, width / 2)
fun Image.rectAngle(): Rectangle = Rectangle(x, y, width, height)

fun action(act: () -> Boolean): Action = object : Action() {
    override fun act(delta: Float): Boolean = act()
}
