package mmorihiro.larger_circle.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import mmorihiro.larger_circle.controller.MainListener
import mmorihiro.larger_circle.model.Values

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            backgroundFPS = 60
            width = Values.width.toInt()
            height = Values.height.toInt()
            title = "BubbleRoad"
            initialBackgroundColor = Color(203f, 250f, 255f, 1f)
        }
        
        LwjglApplication(MainListener(), config)
    }
}
