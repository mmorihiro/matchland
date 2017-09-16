package mmorihiro.matchland.controller.appwarp

import com.shephertz.app42.gaming.multiplayer.client.WarpClient

object ClientHolder {
    init {
        WarpClient.initialize(
                "37e334f70df6e1984fc390d2a52939f75f8e546584c20ef3a31b87efec76d11f",
                "ef2760dcfdbbff89c6d081934e985c26cc05e16dc026146b972ca1a1ad3fc9fc")
    }

    val client = WarpClient.getInstance()!!
    private lateinit var connectionListener: ConnectionListener
    private lateinit var roomListener: RoomListener
    private lateinit var zoneListener: ZoneListener
    private lateinit var notificationListener: NotificationListener
    private var isAdded = false

    fun addListeners(controller: WarpController) {
        if (isAdded) {
            connectionListener.controller = controller
            roomListener.controller = controller
            zoneListener.controller = controller
            notificationListener.controller = controller
        } else {
            connectionListener = ConnectionListener(controller)
            roomListener = RoomListener(controller)
            zoneListener = ZoneListener(controller)
            notificationListener = NotificationListener(controller)
            client.addConnectionRequestListener(connectionListener)
            client.addRoomRequestListener(roomListener)
            client.addZoneRequestListener(zoneListener)
            client.addNotificationListener(notificationListener)
            isAdded = true
        }
    }
}