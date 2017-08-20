package mmorihiro.larger_circle.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.plus
import ktx.actors.then
import mmorihiro.larger_circle.controller.BarController
import mmorihiro.larger_circle.controller.PuzzleController
import mmorihiro.larger_circle.controller.StageChangeController
import mmorihiro.larger_circle.controller.StageChangeEffect
import mmorihiro.larger_circle.model.ConfigModel


class StageView : View() {
    private var currentViews: List<View> = createView()

    private fun createView(): List<View> {
        val barController = BarController({
            val clearView = StageChangeController({
                StageChangeEffect().addEffect(this)
                this + (Actions.delay(0.9f) then Actions.run {
                    ConfigModel.onStageChange()
                    currentViews = createView()
                })
            }).view
            Gdx.input.inputProcessor = clearView
            currentViews += clearView
        })
        val barView = barController.view
        val puzzleView =
                PuzzleController(barController::percentEffect).view
        Gdx.input.inputProcessor = puzzleView
        return listOf(puzzleView, barView)
    }

    override fun draw() {
        currentViews.forEach(Stage::draw)
        super.draw()
    }

    override fun act(delta: Float) {
        super.act(delta)
        currentViews.forEach { it.act(delta) }
    }
}