package mmorihiro.larger_circle.model


class BattleModel {

    fun decideWinner(player: Int,
                     playerType: Pair<Int, Int>,
                     enemy: Int,
                     enemyType: Pair<Int, Int>): Boolean {
        val a = when (playerType) {
            BubbleType.RED.position ->
                when (enemyType) {
                    BubbleType.RED.position -> 1.0
                    BubbleType.GREEN.position -> 1.2
                    BubbleType.BLUE.position -> 0.8
                    else -> error("")
                }
            BubbleType.GREEN.position -> when (enemyType) {
                BubbleType.RED.position -> 0.8
                BubbleType.GREEN.position -> 1.0
                BubbleType.BLUE.position -> 1.2
                else -> error("")
            }
            BubbleType.BLUE.position -> when (enemyType) {
                BubbleType.RED.position -> 1.2
                BubbleType.GREEN.position -> 0.8
                BubbleType.BLUE.position -> 1.0
                else -> error("")
            }
            else -> error("")
        }
        return player * a > enemy
    }
}