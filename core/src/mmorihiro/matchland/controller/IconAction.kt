package mmorihiro.matchland.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.alpha
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.ItemType.WATER
import mmorihiro.matchland.model.Point
import mmorihiro.matchland.model.PuzzleModel
import mmorihiro.matchland.view.ConnectEvent
import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.Puzzle


fun iconReaction(view: Puzzle, connected: List<Point>, isPlayer: Boolean): Unit = view.run {
    val last = connected.last()
    val list = listOf(last) + getAroundPoints(view, last)
    // つなげた数だけプレイヤーの色を増やす
    val lastItem = items[last.second][last.first]
    val itemType = ItemType.values().first { it.position == lastItem.type }
    list.filter {
        val tileType = tiles[it.second][it.first].type
        if (itemType == WATER) tileType == (if (isPlayer) enemyType else playerType).position
        else tileType == WATER.position
    }.take(connected.size).forEach {
        val icon = itemLoader.load(lastItem.type).apply {
            x = lastItem.x
            y = lastItem.y
            view + this
        }
        val tile = tiles[it.second][it.first]
        tile.type = itemType.position
        icon + (delay(0.2f)
                then moveTo(tile.x, tile.y, 0.1f) then Actions.run {
            icon.remove()
            tile + (parallel(scaleTo(1.3f, 1.3f, 0.1f)
                    then scaleTo(1f, 1f, 0.1f), color(itemType.color, 0.1f)))
        })
    }
}

private fun getAroundPoints(view: Puzzle, point: Point) = view.run {
    PuzzleModel().getAroundPoints(point).filter { (x, y) ->
        // 配列の範囲外でないか
        x >= 0 && y >= 0 && y < colSize && x < rowSize
    }
}

fun addNewItems(view: Puzzle, event: ConnectEvent,
                canAddNew: Boolean = true): Unit = view.run {
    val last = event.connectedItems.last()
    val item = items[last.second][last.first]
    items.forEach { row ->
        row.filter { it.color != Color.WHITE }
                .forEach { existingItem ->
                    val action = moveTo(item.x, item.y, 0.2f) then
                            Actions.run {
                                existingItem.remove()
                            }
                    existingItem.color = Color.WHITE
                    existingItem + action
                    val point = coordinateToPoint(
                            existingItem.x.toInt(), existingItem.y.toInt())
                    if (canAddNew)
                        view.items[point.second][point.first] = newItem(view, existingItem)
                }
    }
    resetIcons(event.connectedItems)
    enemyAction(view, event, canAddNew)
}

private fun enemyAction(view: Puzzle, event: ConnectEvent, canAddNew: Boolean) {
    val center = event.enemyPoint.let {
        view.items[it.second][it.first]
    }
    event.enemy.forEach {
        val item = view.items[it.second][it.first]
        val action = moveTo(center.x, center.y, 0.2f) then
                Actions.run {
                    item.remove()
                }
        item + action
        if (canAddNew) view.items[it.second][it.first] = newItem(view, item)
    }
}

private fun newItem(view: Puzzle, existingItem: MyImage): MyImage =
        loadItem(view).apply {
            x = existingItem.x
            y = existingItem.y
            alpha = 0f
            this + fadeIn(0.15f)
            view.itemLayer + this
        }

fun loadItem(view: Puzzle) = view.run {
    itemLoader.load(listOf(playerType, enemyType, WATER)[MathUtils.random(2)].position)
}

