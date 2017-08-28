package mmorihiro.matchland.view

import com.badlogic.gdx.graphics.Color
import ktx.actors.centerPosition
import ktx.scene2d.KTextButton
import ktx.scene2d.textButton
import ktx.scene2d.window
import mmorihiro.matchland.model.Values
import kotlin.properties.Delegates


class StageChangeView(title: String) : View() {
    var button by Delegates.notNull<KTextButton>()
        private set
    val window = window(title) {
        defaults().pad(20f)
        button = textButton("continue") {
            color = Color.GREEN
        }
        pack()
        centerPosition(Values.width, Values.height)
    }
}