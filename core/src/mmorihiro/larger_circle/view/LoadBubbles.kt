package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import ktx.assets.asset


class LoadBubbles(val bubbleSize: Int, val size: Int, filePath: String) {
    val sheet = asset<Texture>(filePath)

    fun load(positions: List<Pair<Int, Int>>): List<Bubble> {
        assert(positions.size == size)
        val tiles = TextureRegion.split(sheet, bubbleSize, bubbleSize)
        return (0..(size - 1)).map {
            val (x, y) = positions[it]
            Bubble(tiles[x][y], x to y)
        }
    }

    fun loadRandom(): List<Bubble> {
        val row = sheet.width / bubbleSize
        val col = sheet.height / bubbleSize
        return load((0..(size - 1)).map {
            MathUtils.random(col - 1) to MathUtils.random(row - 1)
        })
    }
}