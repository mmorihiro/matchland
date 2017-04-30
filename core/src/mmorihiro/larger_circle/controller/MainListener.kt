package mmorihiro.larger_circle.controller

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.assets.Assets
import ktx.assets.asset
import ktx.assets.load
import ktx.assets.unload
import ktx.scene2d.Scene2DSkin
import mmorihiro.larger_circle.view.View


class MainListener : ApplicationAdapter() {
    private var currentViews: List<View> = listOf()

    override fun create() {
        loadAssets()
        Scene2DSkin.defaultSkin = asset<Skin>("ui/uiskin.json")
        val barView = BarController(30, this::record).view
        val mapController = MapController(barView::onTurnEnd, barView::onGet)
        val puzzleView = PuzzleController(mapController::onHit).view
        val mapView = mapController.view
        InputMultiplexer().run {
            addProcessor(puzzleView)
            addProcessor(mapView)
            Gdx.input.inputProcessor = this
        }
        currentViews = listOf(puzzleView, barView, mapView)
    }

    private fun loadAssets() {
        Assets.manager = AssetManager()
        load<Texture>("upBackground.png")
        load<Texture>("background.png")
        load<Texture>("cover.png")
        load<Texture>("bar.png")
        load<Texture>("bubbles.png")
        load<Texture>("pointer.png")
        load<Texture>("tile.png")
        load<Texture>("star.png")
        load<Texture>("starBar.png")
        load<Texture>("lifeBar.png")
        load<Skin>("ui/uiskin.json")
        Assets.manager.finishLoading()
    }

    fun record(star: Int) {
        load<Texture>("recordBack.png")
        load<Texture>("window.png")
        load<Texture>("starFrame.png")
        load<Texture>("recordStar.png")
        Assets.manager.finishLoading()
        currentViews += RecordController(star, {
            currentViews.dropLast(1).forEach { it.pause = true }
        }).view
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        currentViews.forEach { it.act(Gdx.graphics.deltaTime) }
        currentViews.forEach(Stage::draw)
    }

    override fun dispose() {
        unload("upBackground.png")
        unload("background.png")
        unload("cover.png")
        unload("bar.png")
        unload("bubbles.png")
        unload("pointer.png")
        unload("tile.png")
        unload("star.png")
        unload("starBar.png")
        unload("lifeBar.png")
        unload("ui/uiskin.json")
        unload("recordBack.png")
        unload("window.png")
        unload("starFrame.png")
        unload("recordStar.png")
    }
}
