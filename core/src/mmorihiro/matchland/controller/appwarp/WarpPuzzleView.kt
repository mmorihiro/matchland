package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.asset
import mmorihiro.matchland.controller.ScoreController
import mmorihiro.matchland.controller.StageChangeController
import mmorihiro.matchland.controller.StageChangeEffect
import mmorihiro.matchland.model.Point
import mmorihiro.matchland.model.Values
import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.Puzzle
import mmorihiro.matchland.view.View


class WarpPuzzleView(override val onTouchDown: (Puzzle, Int, Int) -> Unit,
                     override val onTouchDragged: (Puzzle, Int, Int) -> Unit,
                     override val onTouchUp: (Puzzle) -> Unit,
                     val onFinish: () -> Unit,
                     val onHome: () -> Unit,
                     val top: View) : Puzzle() {
    override var tiles: List<List<MyImage>> = listOf(listOf())
    override var items: MutableList<MutableList<MyImage>> = mutableListOf(mutableListOf())
    var enemyConnected: List<Point> = listOf()
    var isPlayerTouchUp = false
    var isEnemyTouchUp = false
    val cover = Image(asset<Texture>("backgroundTop.png")).apply {
        y = Values.height - height
    }
    val barController = createBar()
    private var currentViews = listOf<View>(barController.view)

    fun showWindow(title: String) {
        this + Actions.run { buildWindow(title) }
    }

    private fun createBar(): ScoreController =
            ScoreController(onClear = {
                onFinish()
                buildWindow("Win!")
            }, level = 6)

    private fun buildWindow(title: String) {
        val changeView = StageChangeController(title) {
            Gdx.input.inputProcessor = null
            StageChangeEffect().addEffect(top)
            top + (Actions.delay(0.9f) then Actions.run { onHome() })
        }.view
        Gdx.input.inputProcessor = changeView
        currentViews += changeView
    }

    override fun draw() {
        if (tiles.size == 1) return
        super.draw()
        currentViews.forEach(Stage::draw)
    }

    override fun act(delta: Float) {
        if (tiles.size == 1) return
        super.act(delta)
        currentViews.forEach { it.act(delta) }
    }
}