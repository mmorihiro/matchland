package mmorihiro.matchland.model

import com.badlogic.gdx.graphics.Color
import com.squareup.moshi.Moshi
import java.io.File


object ConfigModel {
    private val file = File("config.json")
    val moshi: Moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(Config::class.java)
    var config =
            if (file.exists()) adapter.fromJson(file.readText())!!
            else Config(1, ItemType.FIRE)
        private set

    fun onStageChange() {
        config = config.copy(stageNumber = config.stageNumber + 1)
        file.writeText(adapter.toJson(config))
    }
    
    fun onItemTypeChange(color: Color) {
        config = config.copy(itemType = ItemType.values().first { it.color == color })
        file.writeText(adapter.toJson(config))
    }

    data class Config(val stageNumber: Int, val itemType: ItemType)
}
