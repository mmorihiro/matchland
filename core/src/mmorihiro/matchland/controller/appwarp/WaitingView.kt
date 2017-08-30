package mmorihiro.matchland.controller.appwarp

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.ui.Button
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.scene2d.label
import ktx.scene2d.table
import ktx.scene2d.textButton
import mmorihiro.matchland.model.Values
import mmorihiro.matchland.view.View


class WaitingView : View() {
    lateinit var button: Button
        private set

    init {
        table {
            label("Now Searching...")
            pack()
            centerPosition(Values.width, Values.height)
            y += 10f
            this@WaitingView + this
        }
        table {
            button = textButton("cancel") {
                color = Color.YELLOW
            }
            pack()
            centerPosition(Values.width, Values.height)
            y -= 40f
            this@WaitingView + this
        }
    }
}