package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.WarpClient
import com.shephertz.app42.gaming.multiplayer.client.command.WarpResponseResultCode
import com.shephertz.app42.gaming.multiplayer.client.events.ConnectEvent
import com.shephertz.app42.gaming.multiplayer.client.listener.ConnectionRequestListener


class ConnectionListener(val warpClient: WarpClient) : ConnectionRequestListener {
    override fun onInitUDPDone(p0: Byte) {

    }

    override fun onConnectDone(event: ConnectEvent?) {
        if (event!!.result == WarpResponseResultCode.SUCCESS) {
            warpClient.initUDP()
            warpClient.joinRoomInRange(1, 1, false)
        } else error("connectionError")
    }

    override fun onDisconnectDone(p0: ConnectEvent?) {

    }
}