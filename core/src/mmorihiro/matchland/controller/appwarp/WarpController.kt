package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import io.github.config4k.extract
import io.github.config4k.toConfig
import ktx.actors.alpha
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.matchland.controller.*
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.MessageType
import mmorihiro.matchland.view.ConnectEvent
import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.Puzzle

class WarpController : Controller {
    val warpClient: WarpClient by lazy { WarpClient.getInstance() }
    override val view = WarpPuzzleView(
            { view, x, y ->
                touchAction(view, x, y)
                view.connectEvent?.let {
                    sendMessage(MessageType.CONNECT, x, y)
                    view.connectEvent = it.copy(
                            enemy = (view as WarpPuzzleView).enemyConnected)
                }
            },
            { view, x, y ->
                onTouchDragged(view, x, y,
                        { sendMessage(MessageType.CONNECT, x, y) },
                        { bx, by ->
                            sendMessage(MessageType.NotConnect, bx.toInt(), by.toInt())
                        })
            }, { view ->
        val warpView = view as WarpPuzzleView
        if (warpView.connectEvent == null) return@WarpPuzzleView
        val connectEvent = warpView.connectEvent!!
        onTouchUp(view, { event ->
            sendMessage(MessageType.TouchUp)
            if (warpView.isEnemyTouchUp) {
                warpView.enemyConnected = listOf()
                iconReaction(view, event.enemy, false)
                iconReaction(view, event.connectedItems, true)
                addNewItems(view, event)
                sendNewItems(view, event)
                warpView.isEnemyTouchUp = false
            } else {
                warpView.isPlayerTouchUp = true
                warpView.connectEvent = connectEvent
            }
        }, { sendMessage(MessageType.NotEnough) })
    })

    init {
        WarpClient.initialize(
                "7e6b26f67a247caf422e4740a615b2eb319556979966657807e348746375c9bb",
                "d76b818c1efaa7d447cbf692518095caf040198c3ab1912d5f24eefafe0d7932")
        warpClient.addConnectionRequestListener(ConnectionListener(warpClient))
        warpClient.addRoomRequestListener(RoomListener(this))
        warpClient.addZoneRequestListener(ZoneListener(warpClient))
        warpClient.addNotificationListener(NotificationListener(this))
        warpClient.connectWithUserName(view.playerType.name)
    }

    fun startGame(event: LiveRoomInfoEvent) = view.run {
        enemyType = event.joinedUsers.first { it != playerType.name }.let { typeName ->
            ItemType.values().first { it.name == typeName }
        }
        this + backGround
        this + itemLayer
        val config = ConfigFactory.parseString(event.properties["iconList"].toString())
        val list = config.extract<List<List<Int>>>("list")
        val type0 = event.properties["type0"].toString().let { colorName ->
            ItemType.values().first { it.name == colorName }
        }
        val type1 = if (type0 == enemyType) playerType else enemyType
        tiles = (0 until colSize).map { yIndex ->
            (0 until rowSize).map { xIndex ->
                val itemType = when (yIndex) {
                    0 -> type0
                    colSize - 1 -> type1
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
                        listOf(type0, type1, ItemType.WATER)[type].position)
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

    private fun sendNewItems(view: Puzzle, event: ConnectEvent) {
        val items = (event.enemy + event.connectedItems).map {
            val position = view.items[it.second][it.first].type
            val type = ItemType.values().first { it.position == position }
            NewItem(type, it.first, it.second)
        }
        val str = items.toConfig("items").root().render(ConfigRenderOptions.concise())

        warpClient.sendUpdatePeers("${ConfigModel.config.itemType
                .name}@${MessageType.NewItem.name}@$str".toByteArray())
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
            addNewItems(view, connectEvent!!, false)
            connectEvent = null
            isPlayerTouchUp = false
        } else isEnemyTouchUp = true
    }

    fun onNewItem(newItems: List<NewItem>) = view.run {
        newItems.forEach {
            val tile = tiles[it.y][it.x]
            itemLoader.load(it.item.position).run {
                x = tile.x + padding
                y = tile.y + padding
                items[it.y][it.x] = this
                alpha = 0f
                this + Actions.fadeIn(0.15f)
                itemLayer + this
            }
        }
    }
}