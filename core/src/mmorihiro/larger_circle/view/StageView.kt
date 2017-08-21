package mmorihiro.larger_circle.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.plus
import ktx.actors.then
import ktx.scene2d.Scene2DSkin
import mmorihiro.larger_circle.controller.BarController
import mmorihiro.larger_circle.controller.PuzzleController
import mmorihiro.larger_circle.controller.StageChangeController
import mmorihiro.larger_circle.controller.StageChangeEffect
import mmorihiro.larger_circle.model.ConfigModel
import mmorihiro.larger_circle.model.ItemType
import mmorihiro.larger_circle.model.Values


class StageView : View() {
    private var currentViews: List<View> = createView()
    private val label = Label("Lv ${ConfigModel.config.stageNumber}",
            Scene2DSkin.defaultSkin, "default-font", Color.WHITE).apply {
        x = 53f
        y = Values.height - 37f
        this@StageView + this
    }

    private fun createView(): List<View> {
        val barController = BarController({
            val clearView = StageChangeController({
                Gdx.input.inputProcessor = null
                StageChangeEffect().addEffect(this)
                this + (Actions.delay(0.9f) then Actions.run {
                    ConfigModel.onStageChange()
                    label.setText("Lv ${ConfigModel.config.stageNumber}")
                    currentViews = createView()
                })
            }).view
            Gdx.input.inputProcessor = clearView
            currentViews += clearView
        })
        val barView = barController.view
        val lv = ConfigModel.config.stageNumber
        val enemyType = ItemType.values()
                .filterNot { it == ItemType.FIRE || it == ItemType.WATER }
                .let { it[lv % 3] }
        val puzzleView =
                PuzzleController(barController::percentEffect, ItemType.FIRE, enemyType).view
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