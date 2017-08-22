package mmorihiro.matchland.view

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import ktx.scene2d.Scene2DSkin
import mmorihiro.matchland.controller.BarController
import mmorihiro.matchland.controller.PuzzleController
import mmorihiro.matchland.controller.StageChangeController
import mmorihiro.matchland.controller.StageChangeEffect
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.model.ItemType
import mmorihiro.matchland.model.Values


class StageView(private val onHome: () -> Unit, val top: View) : View() {
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
        val config = ConfigModel.config
        val enemyType = ItemType.values()
                .filterNot { it == config.itemType || it == ItemType.WATER }
                .let { it[config.stageNumber % 3] }
        val puzzleView =
                PuzzleController(barController::percentEffect, {
                    val pauseView = getPauseView()
                    Gdx.input.inputProcessor = pauseView
                    currentViews += pauseView
                }, config.itemType, enemyType).view
        Gdx.input.inputProcessor = puzzleView
        return listOf(puzzleView, barView)
    }

    private fun getPauseView(): PauseView =
            PauseView().apply {
                resumeButton.onClick { _, _ ->
                    currentViews -= currentViews.last()
                    Gdx.input.inputProcessor = currentViews.first()
                }
                resetButton.onClick { _, _ ->
                    Gdx.input.inputProcessor = null
                    StageChangeEffect().addEffect(this@StageView)
                    this@StageView + (Actions.delay(0.9f) then Actions.run {
                        currentViews = createView()
                    })
                }
                homeButton.onClick { _, _ ->
                    Gdx.input.inputProcessor = null
                    StageChangeEffect().addEffect(top)
                    top + (Actions.delay(0.9f) then Actions.run { onHome() })
                }
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