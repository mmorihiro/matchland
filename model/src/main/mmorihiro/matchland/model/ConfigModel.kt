package mmorihiro.matchland.model

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.squareup.moshi.Moshi


object ConfigModel {
    private val file = Gdx.files.local("config.json")
    val moshi: Moshi = Moshi.Builder().build()
    private val adapter = moshi.adapter(Config::class.java)
    var config =
            if (file.exists()) adapter.fromJson(file.readString())!!
            else Config(1, ItemType.FIRE)
        private set

    fun onStageChange() {
        config = config.copy(stageNumber = config.stageNumber + 1)
        file.writeString(adapter.toJson(config), false)
    }
    
    fun onItemTypeChange(color: Color) {
        config = config.copy(itemType = ItemType.values().first { it.color == color })
        file.writeString(adapter.toJson(config), false)
    }

    data class Config(val stageNumber: Int, val itemType: ItemType)
}
