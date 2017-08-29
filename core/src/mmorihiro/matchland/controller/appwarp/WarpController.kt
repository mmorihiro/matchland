package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.typesafe.config.ConfigFactory
import com.typesafe.config.ConfigRenderOptions
import io.github.config4k.extract
import io.github.config4k.toConfig
import ktx.actors.plus
import ktx.assets.asset
import mmorihiro.matchland.controller.*
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.MessageType
import mmorihiro.matchland.view.ConnectEvent
import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.Puzzle
import mmorihiro.matchland.view.View

class WarpController(private val onStart: () -> Unit,
                     onHome: () -> Unit, top: View) : Controller {
    val warpClient: WarpClient by lazy { WarpClient.getInstance() }
    lateinit var roomID: String
    override val view = WarpPuzzleView(
            onTouchDown = { view, x, y ->
                touchAction(view, x, y)
                view.connectEvent?.let {
                    sendMessage(MessageType.CONNECT, x, y)
                    view.connectEvent = it.copy(
                            enemy = (view as WarpPuzzleView).enemyConnected)
                }
            },
            onTouchDragged = { view, x, y ->
                onTouchDragged(view, x, y,
                        { sendMessage(MessageType.CONNECT, x, y) },
                        { bx, by ->
                            sendMessage(MessageType.NotConnect, bx.toInt(), by.toInt())
                        })
            }, onTouchUp = { view ->
        val warpView = view as WarpPuzzleView
        if (warpView.connectEvent == null) return@WarpPuzzleView
        val connectEvent = warpView.connectEvent!!
        onTouchUp(view, { event ->
            sendMessage(MessageType.TouchUp)
            if (view.isEnemyTouchUp) {
                view.enemyConnected = listOf()
                iconReaction(view, event.enemy, false)
                iconReaction(view, event.connectedItems, true)
                addNewItems(view, event)
                sendNewItems(view, event)
                view.isEnemyTouchUp = false
                changeBarValue(view)
            } else {
                view.isPlayerTouchUp = true
                view.connectEvent = connectEvent
            }
        }, { sendMessage(MessageType.NotEnough) })
    }, onFinish = {
        warpClient.unsubscribeRoom(roomID)
        warpClient.leaveRoom(roomID)
        warpClient.disconnect()
    }, onHome = onHome, top = top)

    init {
        WarpClient.initialize(
                "37e334f70df6e1984fc390d2a52939f75f8e546584c20ef3a31b87efec76d11f",
                "ef2760dcfdbbff89c6d081934e985c26cc05e16dc026146b972ca1a1ad3fc9fc")
        warpClient.addConnectionRequestListener(ConnectionListener(warpClient))
        warpClient.addRoomRequestListener(RoomListener(this))
        warpClient.addZoneRequestListener(ZoneListener(warpClient))
        warpClient.addNotificationListener(NotificationListener(this))
        warpClient.connectWithUserName(view.playerType.name)
    }

    fun startGame(event: LiveRoomInfoEvent) = view.run {
        Gdx.input.inputProcessor = null
        onStart()
        StageChangeEffect().resumeEffect(top)
        roomID = event.data.id
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
        this + cover
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
}