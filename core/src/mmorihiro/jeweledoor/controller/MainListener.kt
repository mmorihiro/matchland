package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Stage
import ktx.assets.Assets
import ktx.assets.dispose
import ktx.assets.load


class MainListener : ApplicationAdapter() {
    var currentViews: List<Stage> = listOf()

    override fun create() {
        Assets.manager = AssetManager()
        load<Texture>("bullet.png")
        load<Texture>("cannon.png")
        load<Texture>("background.png")
        load<Texture>("doorBackground.png")
        Assets.manager.finishLoading()
        currentViews = listOf(BasicViewController().view, DoorViewController().view)
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
