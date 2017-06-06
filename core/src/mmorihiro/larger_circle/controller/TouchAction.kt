package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import ktx.actors.alpha
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.ConnectEvent
import mmorihiro.larger_circle.view.PuzzleView

fun touchAction(view: PuzzleView, x: Int, y: Int) = view.run {
    val touchedPoint = coordinateToPoint(x, y)
    val touchedBubble = getBubbleFromPoint(touchedPoint)
    val points = bubbles.mapIndexed { yIndex, row ->
        row.mapIndexed {
            xIndex, bubble ->
            (xIndex to yIndex) to bubble.type
        }
                .filter { it.second == touchedBubble.type }
                .filterNot { it.first.second == 4 }
                .map { it.first }
    }.flatten()
    val sameTypeGroup = PuzzleModel().sameTypeGroup(points).find {
        it.contains(((touchedBubble.x + 8) / tileSize).toInt()
                to ((touchedBubble.y + 8) / tileSize).toInt())
    }!!
    view.connectEvent = ConnectEvent(listOf(touchedPoint), sameTypeGroup)
    touchEffect(view, sameTypeGroup, touchedBubble)
}

private fun touchEffect(view: PuzzleView, sameTypeGroup: Set<Point>,
                        touchedBubble: Bubble) = view.run {
    val color = Color(0.4f, 0.4f, 0.4f, 0.7f)
    bubbles.forEach {
        it.forEach {
            it.color = color
            it.alpha = 0.6f
        }
    }
    sameTypeGroup.forEach { (x, y) ->
        bubbles[y][x].color = Color.WHITE
        bubbles[y][x].alpha = 0.7f
    }
    touchedBubble.alpha = 1.0f
}

fun onTouchUp(view: PuzzleView,
              onHit: (PuzzleView, Pair<Int, Int>, Int) -> Unit) {
    view.connectEvent?.let { (connectedBubbles) ->
        view.connectEvent = null
        val size = connectedBubbles.size
        if (size == 1) view.resetBubbles()
        else onHit(view, view.getBubbleFromPoint(
                connectedBubbles.first()).type, size)
    }
}

fun onTouchDragged(view: PuzzleView, x: Int, y: Int): Unit = view.run {
    connectEvent?.let {
        val point = coordinateToPoint(x, y)
        val bubble = getBubbleFromPoint(point)
        if (it.sameTypeGroup.contains(point)
                && !it.connectedBubbles.contains(point)
                && PuzzleModel().getAround(point,
                listOf(it.connectedBubbles.last())).isNotEmpty()
                && bubble.circle().contains(x.toFloat(), y.toFloat())) {
            bubble.alpha = 1.0f
            connectEvent =
                    it.copy(connectedBubbles = it.connectedBubbles + point)
        }
    }
}
