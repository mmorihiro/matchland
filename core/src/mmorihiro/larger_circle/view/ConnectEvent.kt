package mmorihiro.larger_circle.view

import mmorihiro.larger_circle.model.Point

data class ConnectEvent(val connectedItems: List<Point>,
                        val sameTypeGroup: Set<Point>) {
    init {
        require(connectedItems.isNotEmpty())
        require(sameTypeGroup.isNotEmpty())
    }
    
    val size = connectedItems.size
}