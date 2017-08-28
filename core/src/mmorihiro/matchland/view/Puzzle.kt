package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.assets.asset
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.Point
import kotlin.properties.Delegates


abstract class Puzzle : View() {
    val rowSize = 5
    val colSize = 7
    val tileSize = 51f
    val bottom = 55
    val padding = 8
    val backGround = Image(asset<Texture>("background.png"))
    val itemLoader = ImageLoader(32, "items.png")
    var itemLayer = Group()
    var connectEvent: ConnectEvent? = null
    val playerType = ConfigModel.config.itemType
    var enemyType by Delegates.notNull<ItemType>()
    abstract var tiles: List<List<MyImage>>
    abstract var items: MutableList<MutableList<MyImage>>
    abstract val onTouchUp: (Puzzle) -> Unit
    abstract val onTouchDown: (Puzzle, Int, Int) -> Unit
    abstract val onTouchDragged: (Puzzle, Int, Int) -> Unit

    fun coordinateToPoint(x: Int, y: Int): Point =
            x / tileSize.toInt() to (y - bottom) / tileSize.toInt()

    fun resetIcons(connectedItems: List<Point>) {
        connectedItems.forEach {
            val item = items[it.second][it.first]
            item.color = Color.WHITE
        }
    }

    fun getColorValue(color: Color = playerType.color) =
            tiles.map { it.count { it.color == color } }.sum()

    override fun touchUp(screenX: Int, screenY: Int,
                         pointer: Int, button: Int): Boolean {
        onTouchUp(this)
        return super.touchUp(screenX, screenY, pointer, button)
    }

    override fun touchDragged(screenX: Int, screenY: Int,
                              pointer: Int): Boolean {
        val pos = screenPosToWorldPos(screenX, screenY)
        if (pos.first > padding && pos.second > bottom)
            onTouchDragged(this, pos.first, pos.second)
        return false
    }

    override fun touchDown(screenX: Int, screenY: Int,
                           pointer: Int, button: Int): Boolean {
        val pos = screenPosToWorldPos(screenX, screenY)
        if (pos.first > padding && pos.second > bottom)
            onTouchDown(this, pos.first, pos.second)
        return super.touchDown(screenX, screenY, pointer, button)
    }
}