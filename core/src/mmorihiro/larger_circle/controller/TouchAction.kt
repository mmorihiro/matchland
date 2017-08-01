package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.Item
import mmorihiro.larger_circle.view.PuzzleView

fun touchAction(view: PuzzleView, x: Int, y: Int) = view.run {
    val touchedPoint = coordinateToPoint(x, y)

    val touchedItem = try {
        getBubbleFromPoint(touchedPoint)
    } catch (e: java.lang.IndexOutOfBoundsException) {
        return
    }

    // 同じ種類のアイテムの座標のリスト
    val points = items.mapIndexed { xIndex, row ->
        row.mapIndexed {
            yIndex, item ->
            (xIndex to yIndex) to item.type
        }
                .filter { it.second == touchedItem.type }
                .map { it.first }
    }.flatten()
    val sameTypeGroup = PuzzleModel().sameTypeGroup(points).find {
        it.contains(touchedPoint)
    }!!
    view.connectEvent = ConnectEvent(listOf(touchedPoint), sameTypeGroup)
    touchEffect(touchedItem)
}

private fun touchEffect(item: Item) {
    item.color = Color(0.5f, 0.5f, 0.5f, 0.7f)
}

fun onTouchUp(view: PuzzleView,
              onHit: (PuzzleView, Pair<Int, Int>, Int) -> Unit) {
    view.connectEvent?.let { (connectedBubbles) ->
        view.connectEvent = null
        val size = connectedBubbles.size
        if (size == 1) view.resetBubbles()
        else {
            connectedBubbles.forEach { view.getBubbleFromPoint(it).remove() }
            onHit(view, view.getBubbleFromPoint(
                    connectedBubbles.first()).type, size)
        }
    }
}

fun onTouchDragged(view: PuzzleView, x: Int, y: Int): Unit = view.run {
    connectEvent?.let {
        val point = coordinateToPoint(x, y)
        val bubble = getBubbleFromPoint(point)
        // やり直す時
        if (it.connectedBubbles.size >= 2 && point ==
                it.connectedBubbles[it.connectedBubbles.lastIndex - 1]) {
            getBubbleFromPoint(it.connectedBubbles.last()).color = Color.WHITE
            connectEvent =
                    it.copy(connectedBubbles = it.connectedBubbles.dropLast(1))
        }
        if (canConnect(it, point, bubble, x, y)) {
            touchEffect(bubble)
            connectEvent =
                    it.copy(connectedBubbles = it.connectedBubbles + point)
        }
    }
}

private fun canConnect(event: ConnectEvent, point: Point,
                       item: Item, x: Int, y: Int): Boolean =
        event.sameTypeGroup.contains(point)
                && !event.connectedBubbles.contains(point)
                && PuzzleModel().getAround(point,
                listOf(event.connectedBubbles.last())).isNotEmpty()
                && item.circle().contains(x.toFloat(), y.toFloat())
