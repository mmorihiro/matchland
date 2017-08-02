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
            { view -> onTouchUp(view, { _, _, _ -> addNewItems() }) }).apply {
        this + backGround
        this + itemLayer
        items.forEach { it.forEach { itemLayer + it } }
    }

    fun addNewItems(): Unit = view.run {
        items = items.map { row ->
            row.map { existingItem ->
                if (existingItem.color != Color.WHITE) {
                    existingItem.clear()
                    loader.loadRandom().apply {
                        x = existingItem.x
                        y = existingItem.y
                        alpha = 0f
                        this + Actions.fadeIn(0.15f)
                        itemLayer + this
                    }
                } else existingItem
            }
        }
    }
}