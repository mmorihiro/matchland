package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.assets.asset
import ktx.scene2d.Scene2DSkin
import mmorihiro.matchland.model.ConfigModel


class ScoreView(level: Int) : View() {
    val tile = Image(asset<Texture>("tile.png")).apply {
        x = 190f
        y = 5f
        color = ConfigModel.config.itemType.color
        setScale(0.5f)
    }
    val target = 13 + Math.min(level, 13)
    
    val score = Label("5/$target", Scene2DSkin.defaultSkin, "default-font2", Color.WHITE).apply {
        x = tile.x + 35
        y = tile.y + 3
    }
}