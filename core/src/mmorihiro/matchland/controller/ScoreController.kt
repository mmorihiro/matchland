package mmorihiro.matchland.controller

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn
import com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction
import com.badlogic.gdx.scenes.scene2d.ui.Label
import ktx.actors.plus
import ktx.actors.then
import ktx.scene2d.Scene2DSkin
import mmorihiro.matchland.view.ScoreView
import kotlin.run


class ScoreController(private val onClear: () -> Unit, level: Int) : Controller {
    override val view = ScoreView(level).apply {
        this + tile
        this + score
    }

    fun isClear(value: Int): Boolean = value >= view.target

    fun percentEffect(value: Int) = view.run {
        val before = score.text.split("/").first().toInt()
        val amount = value - before
        score.setText("$value/${view.target}")
        Label(if (amount >= 0) "+$amount" else "$amount",
                Scene2DSkin.defaultSkin, "default-font", Color.WHITE).apply {
            x = score.x + 5
            y = 25f
            this + (fadeIn(0.4f)
                    then fadeOut(0.4f) then Actions.run { this.remove() })
            this@run + this
        }

        this + object : TemporalAction(0.4f, Interpolation.smooth) {
            override fun update(percent: Float) {
                score.setText("${(before + amount * percent).toInt()}/${view.target}")
            }
        }
        if (isClear(value)) onClear()
    }
}