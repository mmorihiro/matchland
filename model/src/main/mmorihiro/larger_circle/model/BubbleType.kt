package mmorihiro.larger_circle.model


enum class BubbleType {
    RED {
        override val position = 0 to 0
    },
    GREEN {
        override val position = 0 to 1
    },
    BLUE {
        override val position = 0 to 2
    };

    abstract val position: Pair<Int, Int>
}