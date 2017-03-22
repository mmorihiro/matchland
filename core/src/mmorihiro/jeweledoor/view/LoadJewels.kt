package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import ktx.assets.asset


class LoadJewels(val jewelSize: Int, val size: Int, filePath: String) {
    val sheet = asset<Texture>(filePath)

    fun load(positions: List<Pair<Int, Int>>): List<Jewel> {
        assert(positions.size == size)
        val tiles = TextureRegion.split(sheet, jewelSize, jewelSize)
        return (0..(size - 1)).map {
            val (x, y) = positions[it]
            Jewel(tiles[x][y], x to y)
        }
    }

    fun loadRandom(): List<Jewel> {
        val row = sheet.width / jewelSize
        val col = sheet.height / jewelSize
        return load((0..(size - 1)).map {
            MathUtils.random(col - 1) to MathUtils.random(row - 1)
        })
    }
}