package mmorihiro.larger_circle.controller

import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.PuzzleView


fun iconReaction(view: PuzzleView, event: ConnectEvent): Unit = view.run {
    val last = event.connectedItems.last()
    val list = listOf(last) + getAroundPoints(view, last)
    // つなげた数だけプレイヤーの色を増やす
    val color = when (items[last.second][last.first].type) {
        ItemType.FIRE.position -> fireColor
        ItemType.THUNDER.position -> thunderColor
        ItemType.WATER.position -> waterColor
        else -> error("")
    }
    list.filter {
        val tileColor = tiles[it.second][it.first].color
        if (color == waterColor) tileColor != waterColor
        else tileColor == waterColor
    }.take(event.size).forEach {
        tiles[it.second][it.first].color = color
    }
}

private fun getAroundPoints(view: PuzzleView, point: Point) = view.run {
    PuzzleModel().getAroundPoints(point).filter { (x, y) ->
        // 配列の範囲外でないか
        x >= 0 && y >= 0 && y < colSize && x < rowSize
    }
}

