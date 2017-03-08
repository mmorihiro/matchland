package mmorihiro.jeweledoor

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import ktx.actors.onClick
import ktx.actors.plus

class MainListener : ApplicationAdapter() {
    lateinit var currentView: View
    lateinit var stage: Stage

    override fun create() {
        currentView = View().apply {
            onClick { _, _, x, y -> println("x:$x, y: $y") }
        }

        stage = Stage(FitViewport(width, height)).apply {
            this + currentView
            Gdx.input.inputProcessor = this
        }
    }

    override fun render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }
}

val width = Gdx.graphics.width.toFloat()
val height = Gdx.graphics.height.toFloat()
