package mmorihiro.jeweledoor.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import mmorihiro.jeweledoor.MainListener

object DesktopLauncher {
    @JvmStatic fun main(arg: Array<String>) {
        val config = LwjglApplicationConfiguration().apply {
            backgroundFPS = 60
            width = 288
            height = 480
            resizable = false
        }
        
        LwjglApplication(MainListener(), config)
    }
}
