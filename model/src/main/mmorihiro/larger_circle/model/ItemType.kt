package mmorihiro.larger_circle.model


enum class ItemType {
    FIRE {
        override val position = 0 to 0
    },
    THUNDER {
        override val position = 0 to 1
    },
    WATER {
        override val position = 0 to 2
    };

    abstract val position: Pair<Int, Int>
}