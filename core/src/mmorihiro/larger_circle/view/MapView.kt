package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.larger_circle.model.CreateMap
import mmorihiro.larger_circle.model.TileType


class MapView : View() {
    val pointer = Image(asset<Texture>("pointer.png")).apply {
        x = 120f
        y = 112f
    }
    val loader = LoadBubbles(32, "bubbles.png")
    val bubbles = Group()
    val tileSize = 34f
    val tiles = Group()
    val stars = Group()

    var nextMap: List<List<TileType>> = listOf()
    var startY = 3

    init {
        CreateMap().nextMap(
                List(8, { List(7, { TileType.Space }) }), 2 to startY).let {
            (List(5, {
                List(7, {
                    if (MathUtils.random(3) == 0) TileType.Star
                    else TileType.Tile
                })
            }) + it.first.drop(5)).mapIndexed { xIndex, col ->
                newCol(col, xIndex).forEach { tile -> tiles + tile }
                newStars(col, xIndex).forEach { star -> stars + star }
            }
            startY = it.second
        }
    }

    fun newCol(col: List<TileType>, xIndex: Int = 8): List<Image> =
            col.mapIndexed { index, it -> index to it }
                    .filterNot { it.second == TileType.Space }
                    .map { (index, _) ->
                        Image(asset<Texture>("tile.png")).apply {
                            x = 52f + xIndex * tileSize
                            y = index * tileSize + 10f
                        }
                    }

    fun newStars(col: List<TileType>, xIndex: Int = 8) =
            col.mapIndexed { index, it -> index to it }
                    .filter { it.second == TileType.Star }
                    .map { (yIndex, _) ->
                        Image(asset<Texture>("star.png")).apply {
                            x = 52f + xIndex * tileSize + 6
                            y = yIndex * tileSize + 16f
                        }
                    }
}