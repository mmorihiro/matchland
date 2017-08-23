package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import ktx.actors.alpha
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.matchland.controller.*
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.MessageType
import mmorihiro.matchland.view.MyImage

class WarpController : Controller {
    val warpClient: WarpClient by lazy { WarpClient.getInstance() }
    override val view = WarpPuzzleView(
            { view, x, y ->
                touchAction(view, x, y)
                sendMessage(MessageType.CONNECT, x, y)
                view.connectEvent = view.connectEvent!!
                        .copy(enemy = (view as WarpPuzzleView).enemyConnected)
            },
            { view, x, y ->
                onTouchDragged(view, x, y,
                        { sendMessage(MessageType.CONNECT, x, y) },
                        { sendMessage(MessageType.NotConnect, x, y) })
            }, { view ->
        val warpView = view as WarpPuzzleView
        val connectEvent = warpView.connectEvent!!
        onTouchUp(view) { event ->
            sendMessage(MessageType.TouchUp)
            if (warpView.isEnemyTouchUp) {
                warpView.enemyConnected = listOf()
                iconReaction(view, /*event.enemy*/ listOf(0 to 0), false)
                iconReaction(view, event.connectedItems, true)
                addNewItems(view, event.copy(enemy = listOf(0 to 0)))
                warpView.isEnemyTouchUp = false
            } else {
                warpView.isPlayerTouchUp = true
                warpView.connectEvent = connectEvent
            }
        }
    })

    init {
        WarpClient.initialize(
                "845968382258e9a4445266412ce4ed557ba650800b4185c9b6d219ea428da498",
                "ace7f46b6f0f7fe9a1e7443cecc3880fe10948244e8fec7d134599d754809633")
        warpClient.addConnectionRequestListener(ConnectionListener(warpClient))
        warpClient.addRoomRequestListener(RoomListener(this))
        warpClient.addZoneRequestListener(ZoneListener(warpClient))
        warpClient.addNotificationListener(NotificationListener(this))
        warpClient.connectWithUserName(view.playerType.name)
    }

    fun startGame(event: LiveRoomInfoEvent) = view.run {
        enemyType = /*event.joinedUsers.first { it != playerType.name }
                .let { typeName ->
            ItemType.values().first { it.name == typeName }
        }*/ ItemType.THUNDER
        this + backGround
        this + itemLayer
        val config = ConfigFactory.parseString(event.properties["iconList"].toString())
        val list = config.extract<List<List<Int>>>("list")
        val type0 = event.properties["type0"].toString().let { colorName ->
            ItemType.values().first { it.name == colorName }
        }
        tiles = (0 until colSize).map { yIndex ->
            (0 until rowSize).map { xIndex ->
                val itemType = when (yIndex) {
                    0 -> type0
                    colSize - 1 -> if (type0 == enemyType) playerType else enemyType
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
        Gdx.input.inputProcessor = this
    }

    private fun sendMessage(type: MessageType, x: Int = -1, y: Int = -1) {
        val str = if (x >= 0 && y >= 0) "{\"x\":$x, \"y\" : $y}" else ""
        warpClient.sendUpdatePeers("${ConfigModel.config.itemType.name}@${type.name}@$str"
                .toByteArray())
    }

    fun getIconList() = view.run {
        (0 until colSize).map { (0 until rowSize).map { MathUtils.random(2) } }
    }

    fun onEnemyConnect(x: Int, y: Int, isConnected: Boolean) = view.run {
        val (xIndex, yIndex) = coordinateToPoint(x, y)
        val icon = items[yIndex][xIndex]
        if (isConnected) {
            icon.alpha = 0f
            connectEvent?.let {
                connectEvent = it.copy(enemy = it.enemy + (xIndex to yIndex))
            }
            enemyConnected += (xIndex to yIndex)
        } else {
            icon.alpha = 1f
            connectEvent?.let {
                connectEvent = it.copy(enemy = it.enemy - (xIndex to yIndex))
            }
            enemyConnected -= (xIndex to yIndex)
        }
    }

    fun onEnemyTouchUp() = view.run {
        if (isPlayerTouchUp) {
            enemyConnected = listOf()
            iconReaction(view, connectEvent!!.connectedItems, true)
            iconReaction(view, connectEvent!!.enemy, false)
            addNewItems(view, connectEvent!!)
            connectEvent = null
            isPlayerTouchUp = false
        }
        isEnemyTouchUp = true
    }
}