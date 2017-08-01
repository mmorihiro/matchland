package mmorihiro.larger_circle.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.alpha
import ktx.actors.plus
import mmorihiro.larger_circle.view.PuzzleView


class PuzzleController : Controller {
    override val view = PuzzleView(
            ::touchAction,
            ::onTouchDragged,
            { view -> onTouchUp(view, { _, _, _ -> addNewItems() }) }).apply {
        this + backGround
        this + itemLayer
        items.forEach { it.forEach { itemLayer + it } }
    }

    /*private fun showAction(view: PuzzleView,
                           type: Pair<Int, Int>, size: Int): Unit = view.run {
        cover.alpha = 0f
        this + cover
        cover + (delay(0.2f) then fadeIn(0.2f) then Actions.run {
            resetBubbles()
            val label = createLabel(size)
            val bubble = createBubble(type)
            bubble + (fadeAction() then Actions.run {
                this - bubble
            })
            label + (fadeAction() then Actions.run {
                this - label
                onHit(size, type, this@PuzzleController::resume)
            })
            this + bubble
            this + label
        })
    }

    private fun fadeAction() =
            delay(0.7f) then parallel(fadeOut(0.3f), moveBy(0f, 10f, 0.3f))*/

    fun addNewItems(): Unit = view.run {
        items = items.mapIndexed { xIndex, it ->
            val remains = it.filter { it.color == Color.WHITE }
            if (remains.size == colSize) remains
            else remains + (0..colSize - 1 - remains.size).map {
                loader.loadRandom().apply {
                    x = xIndex * tileSize + padding
                    y = tileSize * (colSize + it) + bottom
                    alpha = 0f
                    this + Actions.fadeIn(0.15f)
                    itemLayer + this
                }
            }
        }
        moveToRightPoint()
    }

    private fun moveToRightPoint() = view.run {
        items.forEach {
            it.forEachIndexed { index, item ->
                val toY = index * tileSize + padding + bottom
                item + Actions.moveTo(item.x, toY,
                        0.2f * Math.abs(item.y - toY) / tileSize,
                        Interpolation.circleOut)
            }
        }
    }
}