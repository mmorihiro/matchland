package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.ItemType.WATER
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.PuzzleView


fun iconReaction(view: PuzzleView, connected: List<Point>, isPlayer: Boolean): Unit = view.run {
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

private fun getAroundPoints(view: PuzzleView, point: Point) = view.run {
    PuzzleModel().getAroundPoints(point).filter { (x, y) ->
        // 配列の範囲外でないか
        x >= 0 && y >= 0 && y < colSize && x < rowSize
    }
}

