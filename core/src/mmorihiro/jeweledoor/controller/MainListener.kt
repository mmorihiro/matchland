package mmorihiro.jeweledoor.controller

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import ktx.assets.Assets
import ktx.assets.load
import mmorihiro.jeweledoor.view.BasicView


class MainListener : ApplicationAdapter() {
    lateinit var currentView: BasicView

    override fun create() {
        Assets.manager = AssetManager()
        load<Texture>("bullet.png")
        Assets.manager.finishLoading()
        currentView = BasicViewCreator().view
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        currentView.act(Gdx.graphics.deltaTime)
        currentView.draw()
    }

    override fun dispose() {
        currentView.dispose()
    }
}
