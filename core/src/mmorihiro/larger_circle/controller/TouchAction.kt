package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.MyImage
import mmorihiro.larger_circle.view.PuzzleView

fun touchAction(view: PuzzleView, x: Int, y: Int) = view.run {
    val touchedPoint = coordinateToPoint(x, y)
    val touchedItem = try {
        items[touchedPoint.second][touchedPoint.first]
    } catch (e: IndexOutOfBoundsException) {
        return
    }

    // 同じ種類のアイテムの座標のリスト
    val points = items.mapIndexed { yIndex, row ->
        row.mapIndexed {
            xIndex, item ->
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

private fun touchEffect(image: MyImage) {
    image.color = Color(0.5f, 0.5f, 0.5f, 0.7f)
}

fun onTouchUp(view: PuzzleView,
              onHit: (ConnectEvent) -> Unit) {
    view.connectEvent?.let {
        view.connectEvent = null
        val size = it.connectedItems.size
        if (size <= 2) view.resetBubbles()
        else {
            it.connectedItems.forEach {
                view.items[it.second][it.first].remove()
            }
            onHit(it)
        }
    }
}

fun onTouchDragged(view: PuzzleView, x: Int, y: Int): Unit = view.run {
    connectEvent?.let {
        val point = coordinateToPoint(x, y)
        val item = try {
            items[point.second][point.first]
        } catch (e: IndexOutOfBoundsException) {
            return
        }

        // やり直す時
        if (it.connectedItems.size >= 2 && point ==
                it.connectedItems[it.connectedItems.lastIndex - 1]) {
            val point1 = it.connectedItems.last()
            items[point1.second][point1.first].color = Color.WHITE
            connectEvent =
                    it.copy(connectedItems = it.connectedItems.dropLast(1))
        }
        if (canConnect(it, point, item, x, y)) {
            touchEffect(item)
            connectEvent =
                    it.copy(connectedItems = it.connectedItems + point)
        }
    }
}

private fun canConnect(event: ConnectEvent, point: Point,
                       item: MyImage, x: Int, y: Int): Boolean =
        event.sameTypeGroup.contains(point)
                && !event.connectedItems.contains(point)
                // 接しているかどうか
                && PuzzleModel()
                .getAround(event.connectedItems.last(), listOf(point))
                .isNotEmpty()
                && item.circle().contains(x.toFloat(), y.toFloat())