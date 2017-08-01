package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import ktx.assets.asset


class ItemLoader(val size: Int, filePath: String) {
    val sheet = asset<Texture>(filePath)
    val tiles: Array<Array<TextureRegion>> =
            TextureRegion.split(sheet, size, size)

    fun load(positions: List<Pair<Int, Int>>, size: Int): List<Item> {
        assert(positions.size == size)
        return (0..(size - 1)).map {
            load(positions[it])
        }
    }

    fun load(position: Pair<Int, Int>): Item {
        val (x, y) = position
        return Item(tiles[x][y], x to y)
    }

    fun loadRandom(): Item {
        val row = sheet.width / size
        val col = sheet.height / size
        return load(MathUtils.random(col - 1) to MathUtils.random(row - 1))
    }
}