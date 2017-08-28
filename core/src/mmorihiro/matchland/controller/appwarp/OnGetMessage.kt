package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.alpha
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.matchland.controller.addNewItems
import mmorihiro.matchland.controller.iconReaction


fun onEnemyConnect(view: WarpPuzzleView, x: Int, y: Int, isConnected: Boolean) = view.run {
    val (xIndex, yIndex) = coordinateToPoint(x, y)
    val icon = items[yIndex][xIndex]
    if (isConnected) {
        icon.alpha = 0f
        connectEvent?.let {
            connectEvent = it.copy(enemy = it.enemy + (xIndex to yIndex))
        }
        enemyConnected += (xIndex to yIndex)
    } else {
        icon.alpha = 1f
        connectEvent?.let {
            connectEvent = it.copy(enemy = it.enemy - (xIndex to yIndex))
        }
        enemyConnected -= (xIndex to yIndex)
    }
}

fun onEnemyTouchUp(view: WarpPuzzleView) = view.run {
    if (isPlayerTouchUp) {
        enemyConnected = listOf()
        iconReaction(view, connectEvent!!.connectedItems, true)
        iconReaction(view, connectEvent!!.enemy, false)
        addNewItems(view, connectEvent!!, false)
        connectEvent = null
        isPlayerTouchUp = false
        changeBarValue(view)
    } else isEnemyTouchUp = true
}

fun changeBarValue(view: WarpPuzzleView) {
    view + (Actions.delay(1f) then Actions.run {
        view.barController.percentEffect(view.getColorValue())
    })
}

fun isEnemyCleared(view: WarpPuzzleView): Boolean =
        view.barController.isClear(view.getColorValue(view.enemyType.color))

fun onNewItem(view: WarpPuzzleView, newItems: List<NewItem>) = view.run {
    newItems.forEach {
        val tile = tiles[it.y][it.x]
        itemLoader.load(it.item.position).run {
            x = tile.x + padding
            y = tile.y + padding
            items[it.y][it.x] = this
            alpha = 0f
            this + Actions.fadeIn(0.15f)
            itemLayer + this
        }
    }
}

fun getIconList(view: WarpPuzzleView) = view.run {
    (0 until colSize).map { (0 until rowSize).map { MathUtils.random(2) } }
}