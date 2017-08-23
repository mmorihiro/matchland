package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.matchland.controller.Controller
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.view.MyImage

class WarpController : Controller {
    val warpClient: WarpClient by lazy { WarpClient.getInstance() }
    override val view = WarpPuzzleView(
            { _, _, _ -> },
            { _, _, _ -> }, {})

    init {
        WarpClient.initialize(
                "845968382258e9a4445266412ce4ed557ba650800b4185c9b6d219ea428da498",
                "ace7f46b6f0f7fe9a1e7443cecc3880fe10948244e8fec7d134599d754809633")
        warpClient.addConnectionRequestListener(ConnectionListener(warpClient))
        warpClient.addRoomRequestListener(RoomListener(this))
        warpClient.addZoneRequestListener(ZoneListener(warpClient))
        warpClient.connectWithUserName(view.playerType.name)
    }

    fun startGame(event: LiveRoomInfoEvent) = view.run {
        Gdx.input.inputProcessor = this
        enemyType = /*event.joinedUsers.first { it != playerType.name }
                .let { typeName ->
            ItemType.values().first { it.name == typeName }
        }*/ ItemType.THUNDER
        this + backGround
        this + itemLayer
        val list = ConfigFactory.parseString(event.properties["iconList"].toString())
                .extract<List<List<Int>>>("list")
        tiles = (0 until colSize).map { yIndex ->
            (0 until rowSize).map { xIndex ->
                val itemType = when (yIndex) {
                    0 -> playerType
                    colSize - 1 -> enemyType
                    else -> ItemType.WATER
                }
                MyImage(Image(asset<Texture>("tile.png")), itemType.position).apply {
                    x = 19 + xIndex * tileSize
                    y = yIndex * tileSize + bottom
                    color = itemType.color
                }
            }
        }
        items = list.mapIndexed { yIndex, it ->
            it.mapIndexed { xIndex, type ->
                itemLoader.load(
                        listOf(playerType, enemyType, ItemType.WATER)[type].position)
                        .apply {
                            val tile = tiles[yIndex][xIndex]
                            setPosition(tile.x + padding, tile.y + padding)
                        }
            }.toMutableList()
        }.toMutableList()
        tiles.forEach { it.forEach { itemLayer + it } }
        items.forEach { it.forEach { itemLayer + it } }
    }

    fun getIconList() = view.run {
        (0 until colSize).map { (0 until rowSize).map { MathUtils.random(2) } }
    }
}