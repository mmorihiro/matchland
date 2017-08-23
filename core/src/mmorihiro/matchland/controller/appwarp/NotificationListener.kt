package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.events.*
import com.shephertz.app42.gaming.multiplayer.client.listener.NotifyListener
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.MessageType
import java.util.*


class NotificationListener(val controller: WarpController) : NotifyListener {
    override fun onUpdatePeersReceived(event: UpdateEvent?) {
        val list = String(event!!.update).split("@")
        if (list[0] == ConfigModel.config.itemType.name) return
        when (MessageType.values().first { it.name == list[1] }) {
            MessageType.CONNECT -> {
                val config = ConfigFactory.parseString(list[2])
                controller.onEnemyConnect(config.extract("x"),
                        config.extract("y"), true)
            }
            MessageType.NotConnect -> {
                val config = ConfigFactory.parseString(list[2])
                controller.onEnemyConnect(config.extract("x"),
                        config.extract("y"), false)
            }
            MessageType.TouchUp -> controller.onEnemyTouchUp()
        }
    }

    override fun onRoomDestroyed(p0: RoomData?) {

    }

    override fun onGameStopped(p0: String?, p1: String?) {
    }

    override fun onUserJoinedRoom(p0: RoomData?, p1: String?) {
    }

    override fun onPrivateChatReceived(p0: String?, p1: String?) {
    }

    override fun onUserResumed(p0: String?, p1: Boolean, p2: String?) {
    }

    override fun onPrivateUpdateReceived(p0: String?, p1: ByteArray?, p2: Boolean) {
    }

    override fun onRoomCreated(p0: RoomData?) {
    }

    override fun onNextTurnRequest(p0: String?) {
    }

    override fun onUserLeftRoom(p0: RoomData?, p1: String?) {
    }

    override fun onGameStarted(p0: String?, p1: String?, p2: String?) {
    }

    override fun onChatReceived(p0: ChatEvent?) {
    }

    override fun onUserChangeRoomProperty(p0: RoomData?, p1: String?, p2: HashMap<String, Any>?, p3: HashMap<String, String>?) {
    }

    override fun onUserLeftLobby(p0: LobbyData?, p1: String?) {
    }

    override fun onMoveCompleted(p0: MoveEvent?) {
    }

    override fun onUserPaused(p0: String?, p1: Boolean, p2: String?) {
    }

    override fun onUserJoinedLobby(p0: LobbyData?, p1: String?) {
    }
}