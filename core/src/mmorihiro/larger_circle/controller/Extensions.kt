package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.Circle
import com.badlogic.gdx.scenes.scene2d.Actor


fun Actor.circle(): Circle = Circle(x + width / 2, y + height / 2, width / 2)
