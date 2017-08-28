package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode.RESOURCE_NOT_FOUND
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode.SUCCESS
import com.shephertz.app42.gaming.multiplayer.client.events.LiveRoomInfoEvent
import com.shephertz.app42.gaming.multiplayer.client.events.RoomEvent
import com.shephertz.app42.gaming.multiplayer.client.listener.RoomRequestListener
import com.typesafe.config.ConfigRenderOptions
import io.github.config4k.toConfig
import mmorihiro.matchland.model.ConfigModel


class RoomListener(private val controller: WarpController) : RoomRequestListener {
    override fun onJoinRoomDone(event: RoomEvent?) {
        when (event!!.result) {
            SUCCESS -> {
                controller.warpClient.subscribeRoom(event.data.id)
            }
            RESOURCE_NOT_FOUND -> {
                val pair = "iconList" to
                        getIconList(controller.view).toConfig("list").root()
                                .render(ConfigRenderOptions.concise())
                val type0 = "type0" to ConfigModel.config.itemType.name
                controller.warpClient.createRoom("matchland", "shephertz",
                        2, hashMapOf(pair, type0))
            }
            else -> {
                controller.warpClient.disconnect()
                error("onJoinRoom")
            }
        }
    }

    override fun onSubscribeRoomDone(event: RoomEvent?) {
        controller.warpClient.getLiveRoomInfo(event!!.data.id)
    }

    override fun onGetLiveRoomInfoDone(event: LiveRoomInfoEvent?) {
        if (event!!.joinedUsers.size == 2) controller.startGame(event)
    }

    override fun onUnSubscribeRoomDone(p0: RoomEvent?) {

    }

    override fun onLockPropertiesDone(p0: Byte) {

    }

    override fun onUnlockPropertiesDone(p0: Byte) {

    }

    override fun onLeaveAndUnsubscribeRoomDone(p0: RoomEvent?) {

    }

    override fun onUpdatePropertyDone(p0: LiveRoomInfoEvent?) {
    }

    override fun onLeaveRoomDone(p0: RoomEvent?) {
    }

    override fun onJoinAndSubscribeRoomDone(p0: RoomEvent?) {
    }

    override fun onSetCustomRoomDataDone(p0: LiveRoomInfoEvent?) {
    }
}