package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Texture.TextureFilter
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.ui.Image


class Item(region: TextureRegion, val type: Pair<Int, Int>) : Image(region) {
    init {
        region.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear)
    }
}