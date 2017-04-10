package mmorihiro.larger_circle.controller

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import ktx.assets.Assets
import ktx.assets.asset
import ktx.assets.dispose
import ktx.assets.load
import ktx.scene2d.Scene2DSkin


class MainListener : ApplicationAdapter() {
    var currentViews: List<Stage> = listOf()

    override fun create() {
        loadAssets()
        Scene2DSkin.defaultSkin = asset<Skin>("ui/uiskin.json")
        val battleController = BattleController()
        val barView = BarController(originBullets = 16).view
        val shootingView = PuzzleController(battleController::onHit).view
        currentViews = listOf(battleController.view, shootingView, barView)
    }

    private fun loadAssets() {
        Assets.manager = AssetManager()
        load<Texture>("upBackground.png")
        load<Texture>("bullets.png")
        load<Texture>("background.png")
        load<Texture>("bar.png")
        load<Texture>("bubbles.png")
        load<Skin>("ui/uiskin.json")
        Assets.manager.finishLoading()
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        currentViews.forEach { it.act(Gdx.graphics.deltaTime) }
        currentViews.forEach(Stage::draw)
    }

    override fun dispose() {
        currentViews.dispose()
    }
}
