package mmorihiro.larger_circle.controller

import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.Point
import mmorihiro.larger_circle.model.PuzzleModel
import mmorihiro.larger_circle.view.PuzzleView


fun iconReaction(view: PuzzleView, connected: List<Point>): Unit = view.run {
    val last = connected.last()
    val list = listOf(last) + getAroundPoints(view, last)
    // つなげた数だけプレイヤーの色を増やす
    val lastItem = items[last.second][last.first]
    val color = when (lastItem.type) {
        ItemType.FIRE.position -> fireColor
        ItemType.THUNDER.position -> thunderColor
        ItemType.WATER.position -> waterColor
        else -> error("")
    }
    list.filter {
        val tileColor = tiles[it.second][it.first].color
        if (color == waterColor) tileColor != waterColor
        else tileColor == waterColor
    }.take(connected.size).forEach {
        val tile = tiles[it.second][it.first]
        val icon = itemLoader.load(lastItem.type).apply {
            x = lastItem.x
            y = lastItem.y
            view + this
        }
        icon + (delay(0.2f)
                then moveTo(tile.x, tile.y, 0.1f) then Actions.run {
            icon.remove()
            tile + (parallel(scaleTo(1.3f, 1.3f, 0.1f)
                    then scaleTo(1f, 1f, 0.1f), color(color, 0.1f)))
        })
    }
}

private fun getAroundPoints(view: PuzzleView, point: Point) = view.run {
    PuzzleModel().getAroundPoints(point).filter { (x, y) ->
        // 配列の範囲外でないか
        x >= 0 && y >= 0 && y < colSize && x < rowSize
    }
}

