package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.alpha
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.BubbleType
import mmorihiro.larger_circle.model.CreateMap
import mmorihiro.larger_circle.model.TileType
import mmorihiro.larger_circle.view.Bubble
import mmorihiro.larger_circle.view.MapView

class MapController(val onTurnEnd: () -> Unit,
                    val onGet: () -> Unit) : Controller {
    override val view = MapView().apply {
        viewport.camera.translate(0f, -191f, 0f)
        this + tiles
        this + stars
        this + pointer
        this + bubbles
    }

    fun onHit(size: Int, type: Pair<Int, Int>,
              resume: () -> Unit): Unit = view.run {
        val (x, y) = when (type) {
            BubbleType.RED.position -> tileSize to 0f
            BubbleType.GREEN.position -> 0f to tileSize
            BubbleType.BLUE.position -> -tileSize to 0f
            BubbleType.Purple.position -> 0f to -tileSize
            else -> error("")
        }
        val repeatAction = Actions.run {
            val rectangle =
                    Rectangle(pointer.x + x + 10f, pointer.y + y + 10f, 2f, 2f)
            stars.children.find { it.rectAngle().overlaps(rectangle) }?.let {
                it + (moveTo(83f, 256f, 0.3f) then Actions.run { it.remove() })
                onGet()
            }
            if (tiles.children.none { it.rectAngle().overlaps(rectangle) }) {
                pointer.clearActions()
                pointer + (Actions.run { onTurnEnd() }
                        then delay(0.5f) then Actions.run { resume() })
            }
        } then addBubbleAction(type, x, y) then delay(0.3f) then when (type) {
            BubbleType.RED.position -> moveAction(-tileSize)
            BubbleType.BLUE.position -> moveAction(tileSize)
            BubbleType.GREEN.position -> movePointerAction(tileSize)
            BubbleType.Purple.position -> movePointerAction(-tileSize)
            else -> error("")
        } then delay(0.4f)
        pointer + (delay(if (pointer.hasActions()) 0.2f else 0f)
                then repeat(size, repeatAction)
                then Actions.run { onTurnEnd() } then delay(0.5f)
                then Actions.run { resume() })
    }

    private fun addBubbleAction(type: Pair<Int, Int>,
                                x: Float, y: Float) = view.run {
        Actions.run {
            val bubble = loader.load(type).apply {
                setPosition(pointer.x + x + 2, pointer.y + y + 2)
                alpha = 0f
            }
            val circle = bubble.circle().apply { radius = 2f }
            bubbles.children.find { it.circle().overlaps(circle) }?.let {
                it + fadeOut(0.3f) + Actions.run { it.remove() }
            }
            bubble + fadeIn(0.3f)
            bubbles + bubble
            pointerMoveAction(bubble)
        }
    }

    private fun pointerMoveAction(bubble: Bubble) = view.run {
        bubble.onClick { _, _ ->
            if (pointer.hasActions() || bubble.hasActions()) return@onClick
            pointer + moveTo(pointer.x, bubble.y - 2, 0.2f)
            val moveX = pointer.x - (bubble.x - 2)
            bubbles.children.forEach { it + moveBy(moveX, 0f, 0.2f) }
            tiles.children.forEach { it + moveBy(moveX, 0f, 0.2f) }
            stars.children.forEach { it + moveBy(moveX, 0f, 0.2f) }
        }
    }

    private fun moveAction(x: Float) = view.run {
        Actions.run {
            if (x < 0 &&
                    tiles.children.none { it.x > 52 + 8 * tileSize - 5 }) {
                addNewCol()
            }
            bubbles.children.forEach {
                it + (moveBy(x, 0f, 0.20f) then
                        Actions.run { if (it.x < -288f) it.remove() })
            }
            tiles.children.forEach {
                it + (moveBy(x, 0f, 0.20f) then
                        Actions.run { if (it.x < -290f) it.remove() })
            }
            stars.children.forEach {
                it + (moveBy(x, 0f, 0.20f) then
                        Actions.run { if (it.x < -288f) it.remove() })
            }
        }
    }

    private fun addNewCol() = view.run {
        if (nextMap.isEmpty()) {
            CreateMap().nextMap(
                    List(8, { List(7, { TileType.Space }) }),
                    0 to startY).let {
                nextMap = it.first
                startY = it.second
            }
        }
        newCol(col = nextMap.first()).forEach { tile -> tiles + tile }
        newStars(col = nextMap.first()).forEach { stars + it }
        nextMap = nextMap.drop(1)
    }

    private fun movePointerAction(y: Float) = moveBy(0f, y, 0.1f)

}
