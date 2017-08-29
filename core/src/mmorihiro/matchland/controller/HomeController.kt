package mmorihiro.matchland.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.delay
import com.badlogic.gdx.scenes.scene2d.actions.Actions.scaleTo
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.Image
import ktx.actors.onClick
import ktx.actors.plus
import ktx.actors.then
import ktx.assets.Assets
import mmorihiro.matchland.model.ConfigModel
import mmorihiro.matchland.view.HomeView
import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.View


class HomeController(onPlay: () -> Unit,
                     onBattle: () -> Unit,
                     private val top: View) : Controller {
    override val view = HomeView().apply {
        Gdx.input.inputProcessor = this
        this + backGround
        this + playButton
        this + battleButton
        icons.forEach {
            this + it.first
            this + it.second
        }
        icons.first { it.first.color == ConfigModel.config.itemType.color }
                .second.color = Color.WHITE
        icons.forEach {
            it.first.onClick { _, tile -> onClick(this, it.second, tile) }
            it.second.onClick { _, icon -> onClick(this, icon, it.first) }
        }
        playButton.onClick { _, button -> buttonOnClick(button, onPlay, false) }
        battleButton.onClick { _, button -> buttonOnClick(button, onBattle, true) }
    }

    private fun buttonOnClick(button: Button, f: () -> Unit, stop: Boolean) {
        button.color = Color(0.5f, 0.5f, 0.5f, 1f)
        button + Actions.color(Color.WHITE, 0.2f)
        Gdx.input.inputProcessor = null
        Assets.manager.finishLoading()
        StageChangeEffect().addEffect(top, stop)
        top + (delay(0.9f) then Actions.run { f() })
    }

    private fun onClick(view: HomeView, icon: MyImage, tile: Image) {
        if (icon.color != Color.WHITE) {
            view.icons.forEach { it.second.color = Color.BLACK }
            icon.color = Color.WHITE
            tile + (scaleTo(1.3f, 1.3f, 0.1f) then scaleTo(1.0f, 1.0f, 0.1f))
            ConfigModel.onItemTypeChange(tile.color)
        }
    }
}