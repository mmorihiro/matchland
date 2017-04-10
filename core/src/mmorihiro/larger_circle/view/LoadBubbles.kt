package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import ktx.assets.asset


class LoadBubbles(val bubbleSize: Int, filePath: String) {
    val sheet = asset<Texture>(filePath)
    val tiles: Array<Array<TextureRegion>> =
            TextureRegion.split(sheet, bubbleSize, bubbleSize)

    fun load(positions: List<Pair<Int, Int>>, size: Int): List<Bubble> {
        assert(positions.size == size)
        return (0..(size - 1)).map {
            load(positions[it])
        }
    }

    fun load(position: Pair<Int, Int>): Bubble {
        val (x, y) = position
        return Bubble(tiles[x][y], x to y)
    }

    fun loadRandom(): Bubble {
        val row = sheet.width / bubbleSize
        val col = sheet.height / bubbleSize
        return load(MathUtils.random(col - 1) to MathUtils.random(row - 1))
    }
}