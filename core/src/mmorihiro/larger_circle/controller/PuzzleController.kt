package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.alpha
import ktx.actors.plus
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController : Controller {
    override val view = PuzzleView(
            ::touchAction,
            ::onTouchDragged,
            { view ->
                onTouchUp(view, { event ->
                    iconReaction(view, event)
                    addNewItems()
                })
            }).apply {
        this + backGround
        this + itemLayer
        bubbles.forEach {
            it.forEach { itemLayer + it }
        }
        items.forEach { it.forEach { itemLayer + it } }
    }

    fun addNewItems(): Unit = view.run {
        items = items.map { row ->
            row.map { existingItem ->
                if (existingItem.color != Color.WHITE) {
                    existingItem.clear()
                    itemLoader.loadRandom().apply {
                        x = existingItem.x
                        y = existingItem.y
                        alpha = 0f
                        this + Actions.fadeIn(0.15f)
                        itemLayer + this
                    }
                } else existingItem
            }
        }
        resetBubbles()
    }
}