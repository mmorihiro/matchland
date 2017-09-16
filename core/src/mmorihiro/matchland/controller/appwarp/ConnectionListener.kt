package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener


class ConnectionListener(var controller: WarpController) : ConnectionRequestListener {
    override fun onInitUDPDone(p0: Byte) {

    }

    override fun onConnectDone(event: ConnectEvent?) {
        if (event!!.result == WarpResponseResultCode.SUCCESS) {
            if (controller.canceled) controller.warpClient.disconnect()
            else controller.warpClient.joinRoomInRange(1, 1, false)
        } else {
            controller.onLobbyError(this.javaClass.kotlin.simpleName, event.result)
        }
    }

    override fun onDisconnectDone(p0: ConnectEvent?) {

    }
}