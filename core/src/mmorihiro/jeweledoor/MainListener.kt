package mmorihiro.jeweledoor

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.Assets
import ktx.assets.load


class MainListener : ApplicationAdapter() {
    lateinit var currentView: View

    override fun create() {
        Assets.manager = AssetManager()
        load<Texture>("bullet.png")
        Assets.manager.finishLoading()

        currentView = View().apply {
            backGround.onClick { _, _, clickedX, clickedY ->
                val ySide = (clickedY - Values.centerY).toDouble()
                val xSide = (clickedX - Values.centerX).toDouble()
                val direction = Math.atan2(ySide, xSide)
                val vx = Math.cos(direction).toFloat()
                val vy = Math.sin(direction).toFloat()
                shoot().let {
                    val actions =
                            Actions.moveBy(vx * 280, vy * 280, 0.9f) then
                                    Actions.run { removeBullet(it) }
                    it + actions
                }
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

object Values {
    val width = Gdx.graphics.width.toFloat()
    val height = Gdx.graphics.height.toFloat()
    val centerX = width / 2
    val centerY = height / 2
}