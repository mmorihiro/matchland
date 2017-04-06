package mmorihiro.larger_circle.controller

import ktx.actors.plus
import mmorihiro.larger_circle.view.BattleView

class BattleViewController : ViewController {
    override val view = BattleView().apply {
        viewport.camera.translate(0f, -288f, 0f)
        this + background
        this + ground
        this + playerCannon
    }

    fun onHit(type: Pair<Int, Int>) {
        //  view.updatePlayer(view.player.newBubble(type))
    }

    fun decideWinner() {
        /* view.run {
             val winner = BattleModel().decideWinner(
                     player.number, player.type, enemy.number, enemy.type)
             println(winner)
         }*/
    }
}
