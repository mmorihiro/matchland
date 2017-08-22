package mmorihiro.larger_circle.view

import com.badlogic.gdx.graphics.Color
import ktx.actors.centerPosition
import ktx.actors.plus
import ktx.scene2d.*
import mmorihiro.larger_circle.model.Values
import kotlin.properties.Delegates


class PauseView : View() {
    var resumeButton by Delegates.notNull<KTextButton>()
        private set
    var resetButton by Delegates.notNull<KTextButton>()
        private set
    var homeButton by Delegates.notNull<KTextButton>()
        private set
    init {
        window(title = "Paused") {
            defaults().pad(5f)
            defaults().padLeft(25f)
            defaults().padRight(25f)
            resumeButton = textButton("Resume") {
                color = Color.GREEN
            }
            row()
            resetButton = textButton("  Reset  ") {
                color = Color.GREEN
            }
            row()
            homeButton = textButton("  Home  ") {
                color = Color.GREEN
            }
            pack()
            centerPosition(Values.width, Values.height)
            this@PauseView + this
        }
    }
}