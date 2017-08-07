package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn
import com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo
import ktx.actors.alpha
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController : Controller {
    override val view = PuzzleView(
            ::touchAction,
            ::onTouchDragged,
            { view ->
                onTouchUp(view, { event ->
                    iconReaction(view, event)
                    addNewItems(event)
                })
            }).apply {
        this + backGround
        this + itemLayer
        tiles.forEach { it.forEach { itemLayer + it } }
        items.forEach { it.forEach { itemLayer + it } }
    }

    private fun addNewItems(event: ConnectEvent): Unit = view.run {
        val last = event.connectedItems.last()
        val item = items[last.second][last.first]
        items = items.map { row ->
            row.map { existingItem ->
                if (existingItem.color != Color.WHITE) {
                    val action = moveTo(item.x, item.y, 0.2f) then
                            Actions.run {
                                existingItem.remove()
                            }
                    existingItem.color = Color.WHITE
                    existingItem + action
                    itemLoader.loadRandom().apply {
                        x = existingItem.x
                        y = existingItem.y
                        alpha = 0f
                        this + fadeIn(0.15f)
                        itemLayer + this
                    }
                } else existingItem
            }
        }
        resetBubbles()
    }
}