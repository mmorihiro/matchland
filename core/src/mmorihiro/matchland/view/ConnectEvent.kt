package mmorihiro.matchland.view

import mmorihiro.matchland.model.Point

data class ConnectEvent(val connectedItems: List<Point>,
                        val sameTypeGroup: Set<Point>,
                        val enemy: List<Point>) {
    init {
        require(connectedItems.isNotEmpty())
        require(sameTypeGroup.isNotEmpty())
    }

    val enemyPoint = enemy.last()
}