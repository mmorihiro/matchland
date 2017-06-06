package mmorihiro.larger_circle.view

import mmorihiro.larger_circle.model.Point

data class ConnectEvent(val connectedBubbles: List<Point>,
                        val sameTypeGroup: Set<Point>) {
    init {
        require(connectedBubbles.isNotEmpty())
        require(sameTypeGroup.isNotEmpty())
    }
}