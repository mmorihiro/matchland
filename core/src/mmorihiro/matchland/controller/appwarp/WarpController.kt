package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode.*
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.squareup.moshi.Types
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog
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
import java.lang.reflect.Type

class WarpController(private val onStart: () -> Unit,
                     private val onHome: () -> Unit, private val top: View) : Controller {
    val warpClient: WarpClient by lazy { WarpClient.getInstance() }
    private lateinit var roomID: String
    private val dialogs = GDXDialogsSystem.install()
    private var errorDialogShown = false
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
        warpClient.addConnectionRequestListener(ConnectionListener(this))
        warpClient.addRoomRequestListener(RoomListener(this))
        warpClient.addZoneRequestListener(ZoneListener(this))
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
        val adapter = ConfigModel.moshi.adapter(IconList::class.java)
        val list =
                adapter.fromJson(event.properties["iconList"].toString())!!.list
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
        val adapter = ConfigModel.moshi.adapter(Point::class.java)
        val json = if (x >= 0 && y >= 0) adapter.toJson(Point(x, y)) else ""
        warpClient.sendUpdatePeers("${ConfigModel.config.itemType.name}@${type.name}@$json"
                .toByteArray())
    }

    private fun sendNewItems(view: Puzzle, event: ConnectEvent) {
        val items = (event.enemy + event.connectedItems).map {
            val position = view.items[it.second][it.first].type
            val type = ItemType.values().first { it.position == position }
            NewItem(type, it.first, it.second)
        }
        val type: Type =
                Types.newParameterizedType(List::class.java, NewItem::class.java)
        val adapter = ConfigModel.moshi.adapter<List<NewItem>>(type)
        val str = adapter.toJson(items)

        warpClient.sendUpdatePeers("${ConfigModel.config.itemType
                .name}@${MessageType.NewItem.name}@$str".toByteArray())
    }

    internal fun onLobbyError(where: String?, result: Byte) {
        if(errorDialogShown) return
        else errorDialogShown = true

        warpClient.leaveLobby()
        warpClient.disconnect()

        val (resultCode, resultText) = when(result) {
            AUTH_ERROR -> "AUTH_ERROR" to "The session id sent in the request was incorrect. This can happen if the client connects without initializing with the correct keys."
            RESOURCE_MOVED -> "RESOURCE_MOVED" to "The resource for which the request was intended to has moved to a state where it can’t process the request. For example, if a client sends a chat or updatePeers message and the connected user is not present in any room."
            UNKNOWN_ERROR -> "UNKNOWN_ERROR" to "This is an unexpected error. Retrying the request is recommended if this happens."
            BAD_REQUEST -> "BAD_REQUEST" to "This occurs if the parameters passed to the request are invalid. For example if null or empty string is passed in the roomId parameter of a joinRoom request."
            CONNECTION_ERROR -> "CONNECTION_ERROR" to "This occurs if the underlying TCP connection with AppWarp cloud service got broken. The client will need to reconnect to the service and retry the operation."
            SUCCESS_RECOVERED -> "SUCCESS_RECOVERED" to "This occurs if when successfully recover a session."
            CONNECTION_ERROR_RECOVERABLE -> "CONNECTION_ERROR_RECOVERABLE" to "This occurs if the underlying TCP connection with AppWarp cloud service got broken but same session can be recovered. The client will need call RecoverConnection() to resume same session."
            USER_PAUSED_ERROR -> "USER_PAUSED_ERROR" to ""
            AUTO_RECOVERING -> "AUTO_RECOVERING" to ""
            else -> "Unknown error " + result to ""
        }

        val bDialog = dialogs.newDialog(GDXButtonDialog::class.java)
        bDialog.setTitle("Error")
        bDialog.setMessage("Failed to connect to server($where): $resultCode\n$resultText")
        bDialog.addButton("OK")

        bDialog.setClickListener {
            errorDialogShown = false
            Gdx.app.postRunnable {
                onHome()
                StageChangeEffect().resumeEffect(top)
            }
        }

        bDialog.build().show()
    }
}