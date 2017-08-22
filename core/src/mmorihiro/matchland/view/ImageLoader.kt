package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset


class ImageLoader(private val size: Int, filePath: String) {
    private val sheet = asset<Texture>(filePath)
    private val tiles: Array<Array<TextureRegion>> =
            TextureRegion.split(sheet, size, size)

    fun load(position: Pair<Int, Int>): MyImage {
        val (x, y) = position
        return MyImage(Image(tiles[x][y]), x to y)
    }

    fun loadRandom(): MyImage {
        val row = sheet.width / size
        val col = sheet.height / size
        return load(MathUtils.random(col - 1) to MathUtils.random(row - 1))
    }
}