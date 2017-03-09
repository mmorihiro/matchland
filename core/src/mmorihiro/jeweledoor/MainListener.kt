package mmorihiro.jeweledoor

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import ktx.actors.onClick
import ktx.actors.plus
import ktx.assets.Assets
import ktx.assets.load


class MainListener : ApplicationAdapter() {
    lateinit var currentView: View

    override fun create() {
        Assets.manager = AssetManager()
        load<Texture>("bullet.png")
        Assets.manager.finishLoading()

        currentView = View().apply {
            backGround.onClick { _, _, x, y ->
                println("clicked $x, $y")
                shoot()
            }
            this + backGround
            Gdx.input.inputProcessor = this
        }
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

val width = Gdx.graphics.width.toFloat()
val height = Gdx.graphics.height.toFloat()
