package mmorihiro.larger_circle.model

import com.badlogic.gdx.math.MathUtils


class CreateMap {
    tailrec fun nextMap(list: List<List<TileType>>,
                        point: Point): Pair<List<List<TileType>>, Int> {
        val mutable = list.map { it.toMutableList() }.toMutableList()
        if (mutable[point.first][point.second] == TileType.Space) {
            mutable[point.first][point.second] = TileType.Tile
        }
        val (x, y) = when (MathUtils.random(3)) {
            0 -> 1 to 0
            1 -> 0 to 1
            2 -> -1 to 0
            3 -> 0 to -1
            else -> error("")
        }
        val next = (point.first + x) to (point.second + y)
        return if (next.first < 0
                || next.second < 0 || next.second > list.first().lastIndex) {
            nextMap(mutable, point)
        } else if (next.first > list.lastIndex) {
            mutable to next.second
        } else {
            mutable[next.first][next.second] =
                    if (MathUtils.random(3) == 0) TileType.Star
                    else TileType.Tile
            nextMap(mutable, next)
        }
    }
}