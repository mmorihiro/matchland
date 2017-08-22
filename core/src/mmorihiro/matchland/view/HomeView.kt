package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.centerPosition
import ktx.assets.asset
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.Values


class HomeView : View() {
    val backGround = Image(asset<Texture>("homeBackground.png"))
    private val itemLoader = ImageLoader(32, "items.png")
    val icons = ItemType.values()
            .filterNot { it == ItemType.WATER }
            .mapIndexed { index, it ->
                val item = itemLoader.load(it.position).apply {
                    x = 50 + 51f * index
                    y = 50f
                    color = Color.BLACK
                }
                Image(asset<Texture>("tile.png")).apply {
                    color = it.color
                    setPosition(item.x - 9, item.y - 9)
                } to item
            }

    val button = Button(Image(asset<Texture>("play.png")).drawable).apply {
        centerPosition(Values.width, Values.height)
    }
}