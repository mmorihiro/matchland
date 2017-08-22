package mmorihiro.matchland.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import mmorihiro.matchland.controller.MainListener
import mmorihiro.matchland.model.Values

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            backgroundFPS = 60
            width = Values.width.toInt()
            height = Values.height.toInt()
            title = "MatchLand"
            initialBackgroundColor = Color(0f, 136f, 170f, 1f)
        }
        
        LwjglApplication(MainListener(), config)
    }
}
