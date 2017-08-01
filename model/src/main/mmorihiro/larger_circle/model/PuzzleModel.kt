package mmorihiro.larger_circle.model

typealias Point = Pair<Int, Int>

class PuzzleModel {
    fun sameTypeGroup(groups: List<Point>): Set<Set<Point>> {
        val around = groups.map {
            getAround(it, groups - it) + it
        }
        return around.foldRight(listOf(around.first())) { set, fold ->
            val same = fold.filter { (set + it).size != it.size + set.size }
            val flat = same.map { it + set }.flatten().toSet() + set
            listOf(flat) + (fold - same)
        }.toSet()
    }

    fun getAround(point: Point, others: List<Point>): Set<Point> =
            others.filter {
                val pattern = setOf(1 to 0, 0 to 1, 0 to -1, -1 to 0) +
                        (if (point.second % 2 == 1) setOf(-1 to 1, -1 to -1)
                        else setOf(1 to 1, 1 to -1))
                pattern.any { (x, y) ->
                    point.first + x to point.second + y == it
                }
            }.toSet().apply {
                if (others.size == 1 && this.isEmpty()) {
                    println(point to others.first())
                }
            }
}

