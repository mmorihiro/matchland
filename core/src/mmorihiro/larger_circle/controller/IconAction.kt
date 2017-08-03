package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.PuzzleView


fun iconReaction(view: PuzzleView, event: ConnectEvent): Unit = view.run {
    val item = event.connectedItems.first()
    when (items[item.second][item.first].type) {
        ItemType.FIRE.position -> bubbleReaction(view, event)
        ItemType.THUNDER.position -> TODO()
        ItemType.WATER.position -> extendReaction(view, event)
    }
}

private fun bubbleReaction(view: PuzzleView, event: ConnectEvent) = view.run {
    val last = event.connectedItems.last()
    val list = listOf(last) + getAroundPoints(view, last)
    // つなげた数だけプレイヤーの色を増やす
    list.filter { bubbles[it.second][it.first].color == Color.GOLD }
            .take(event.size)
            .forEach {
                bubbles[it.second][it.first].color = Color.GREEN
            }
}

private fun extendReaction(view: PuzzleView, event: ConnectEvent) = view.run {
    val last = event.connectedItems.last()
    var set = getAroundPoints(view, last).toSet()
    set.take(event.size - 3).forEach { set += getAroundPoints(view, it) }
    set.forEach {
        bubbles[it.second][it.first].color =
                bubbles[last.second][last.first].color
    }
}


private fun getAroundPoints(view: PuzzleView, point: Point) = view.run {
    PuzzleModel().getAroundPoints(point).filter { (x, y) ->
        // 配列の範囲外でないか
        x >= 0 && y >= 0 && y < colSize && x < rowSize
    }
}

