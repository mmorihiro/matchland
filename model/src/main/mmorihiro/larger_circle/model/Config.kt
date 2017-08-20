package mmorihiro.larger_circle.model

import com.typesafe.config.ConfigFactory
import io.github.config4k.extract
import io.github.config4k.toConfig
import java.io.File


object ConfigModel {
    private val file = File("config.conf")
    var config =
            if (file.exists()) ConfigFactory.parseFile(file).extract("config")
            else Config(1)
        private set

    fun onStageChange() {
        config = config.copy(stageNumber = config.stageNumber + 1)
        file.writeText(config.toConfig("config").root().render())
    }

    data class Config(val stageNumber: Int)
}
