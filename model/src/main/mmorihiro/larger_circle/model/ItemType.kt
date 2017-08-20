package mmorihiro.larger_circle.model

import com.badlogic.gdx.graphics.Color


enum class ItemType {
    FIRE {
        override val position = 0 to 0
        override val color = Color(255 / 255f, 193 / 255f, 132 / 255f, 1f)
    },
    THUNDER {
        override val position = 0 to 1
        override val color = Color(255 / 255f, 255 / 255f, 188 / 255f, 1f)
    },
    WATER {
        override val position = 0 to 2
        override val color = Color(147 / 255f, 255 / 255f, 255 / 255f, 0.5f)
    },
    LEAF {
        override val position = 0 to 3
        override val color = Color(191 / 255f, 255 / 255f, 127 / 255f, 1f)
    },
    CLOUD {
        override val position = 0 to 4
        override val color = Color.WHITE!!
    };

    abstract val position: Pair<Int, Int>
    abstract val color: Color
}