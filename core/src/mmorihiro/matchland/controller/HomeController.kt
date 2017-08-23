package mmorihiro.matchland.controller

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.actions.Actions
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
        playButton.onClick { _, button -> buttonOnClick(button, onPlay) }
        battleButton.onClick { _, button -> buttonOnClick(button, onBattle) }
    }

    private fun buttonOnClick(button: Button, f: () -> Unit) {
        button.color = Color(0.5f, 0.5f, 0.5f, 1f)
        button + Actions.color(Color.WHITE, 0.2f)
        Gdx.input.inputProcessor = null
        Assets.manager.finishLoading()
        StageChangeEffect().addEffect(top)
        top + (Actions.delay(0.9f) then Actions.run { f() })
    }

    private fun onClick(view: HomeView, icon: MyImage, tile: Image) {
        if (icon.color != Color.WHITE) {
            view.icons.forEach { it.second.color = Color.BLACK }
            icon.color = Color.WHITE
            ConfigModel.onItemTypeChange(tile.color)
        }
    }
}