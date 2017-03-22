package mmorihiro.jeweledoor.view

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset


class LoadJewels {
    fun getJewels(jewelSize: Int, size: Int, filePath: String): List<Image> {
        val sheet = asset<Texture>(filePath)
        val row = sheet.width / jewelSize
        val col = sheet.height / jewelSize
        val tiles = TextureRegion.split(sheet, jewelSize, jewelSize)
        return (0..(size - 1)).map {
            Image(tiles[
                    MathUtils.random(col - 1)][MathUtils.random(row - 1)])
        }
    }
}