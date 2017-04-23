package mmorihiro.larger_circle.controller

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.alpha
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.BubbleType
import mmorihiro.larger_circle.view.MapView

class MapController : Controller {
    override val view = MapView().apply {
        viewport.camera.translate(0f, -195f, 0f)
        this + tiles
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
            val rectangle = Rectangle(pointer.x + x + 2f,
                    pointer.y + y + 2f, 2f, 2f)
            if (tiles.children.none { it.rectAngle().overlaps(rectangle) }) {
                pointer.clearActions()
                pointer + (delay(0.5f) then Actions.run { resume() })
            }
        } then addBubbleAction(type, x, y) then delay(0.3f) then when (type) {
            BubbleType.RED.position -> moveAction(-tileSize)
            BubbleType.BLUE.position -> moveAction(tileSize)
            BubbleType.GREEN.position -> movePointerAction(tileSize)
            BubbleType.Purple.position -> movePointerAction(-tileSize)
            else -> error("")
        } then delay(0.4f)
        pointer + (repeat(size, repeatAction) then delay(0.5f)
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
            bubbles.children.find {
                it.circle().overlaps(circle)
            }?.let {
                it + (fadeOut(0.3f)
                        then Actions.run { it.remove() })
            }
            bubble + fadeIn(0.3f)
            bubbles + bubble
        }
    }

    private fun moveAction(x: Float) =
            Actions.run {
                view.bubbles.children.forEach {
                    it + moveBy(x, 0f, 0.1f)
                }
                view.tiles.children.forEach { it + moveBy(x, 0f, 0.1f) }
            }

    private fun movePointerAction(y: Float) = moveBy(0f, y, 0.1f)

}
