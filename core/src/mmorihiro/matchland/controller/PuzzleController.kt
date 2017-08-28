package mmorihiro.matchland.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.delay
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.view.PuzzleView


class PuzzleController(onTurnEnd: (Int) -> Unit,
                       onPause: () -> Unit,
                       enemyType: ItemType) : Controller {
    override val view = PuzzleView(
            { view, x, y -> touchAction(view, x, y) },
            { view, x, y -> onTouchDragged(view, x, y) },
            { view ->
                onTouchUp(view, { event ->
                    iconReaction(view, event.connectedItems, true)
                    iconReaction(view, event.enemy, false)
                    addNewItems(view, event)
                    view + (delay(1f) then Actions.run {
                        onTurnEnd(view.getColorValue())
                    })
                })
            }).apply {
        this.enemyType = enemyType
        this + backGround
        this + itemLayer
        tiles.forEach { it.forEach { itemLayer + it } }
        items.forEach { it.forEach { itemLayer + it } }
        this + pauseButton
        pauseButton.onClick { _, _ -> onPause() }
    }
}