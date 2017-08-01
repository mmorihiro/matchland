package mmorihiro.larger_circle.model


enum class ItemType {
    RED {
        override val position = 0 to 0
    },
    GREEN {
        override val position = 0 to 1
    },
    BLUE {
        override val position = 0 to 2
    },
    Purple {
        override val position = 0 to 3
    };

    abstract val position: Pair<Int, Int>
}