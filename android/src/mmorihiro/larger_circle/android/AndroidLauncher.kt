package mmorihiro.larger_circle.android

import android.os.Bundle

import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.badlogic.gdx.backends.android.surfaceview.FixedResolutionStrategy
import mmorihiro.larger_circle.controller.MainListener
import mmorihiro.larger_circle.model.Values

class AndroidLauncher : AndroidApplication() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val config = AndroidApplicationConfiguration().apply {
            useAccelerometer = false
            useCompass = false
        }

        initialize(MainListener(), config)
    }
}
