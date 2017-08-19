package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import ktx.actors.centerPosition
import ktx.scene2d.KTextButton
import ktx.scene2d.textButton
import ktx.scene2d.window
import mmorihiro.larger_circle.model.Values
import kotlin.properties.Delegates


class StageChangeView : View() {
    var button by Delegates.notNull<KTextButton>()
        private set
    val window = window(title = "Stage Completed!") {
        defaults().pad(20f)
        button = textButton("continue") {
            color = Color.GREEN
        }
        pack()
        centerPosition(Values.width, Values.height)
    }
}