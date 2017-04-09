package mmorihiro.larger_circle.model


class BattleModel {

    fun decideReaction(playerType: Pair<Int, Int>,
                       enemyType: Pair<Int, Int>): Reaction =
            when (playerType) {
            BubbleType.RED.position ->
                when (enemyType) {
                    BubbleType.RED.position -> Reaction.NORMAL
                    BubbleType.GREEN.position -> Reaction.WIN
                    BubbleType.BLUE.position -> Reaction.DEFEAT
                    else -> error("")
                }
            BubbleType.GREEN.position -> when (enemyType) {
                BubbleType.RED.position -> Reaction.DEFEAT
                BubbleType.GREEN.position -> Reaction.NORMAL
                BubbleType.BLUE.position -> Reaction.WIN
                else -> error("")
            }
            BubbleType.BLUE.position -> when (enemyType) {
                BubbleType.RED.position -> Reaction.WIN
                BubbleType.GREEN.position -> Reaction.DEFEAT
                BubbleType.BLUE.position -> Reaction.NORMAL
                else -> error("")
            }
            else -> error("")
        }
}

enum class Reaction {
    NORMAL,
    WIN,
    DEFEAT 
}