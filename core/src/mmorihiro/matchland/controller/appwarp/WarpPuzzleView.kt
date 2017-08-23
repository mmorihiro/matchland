package mmorihiro.matchland.controller.appwarp

import mmorihiro.matchland.view.MyImage
import mmorihiro.matchland.view.Puzzle


class WarpPuzzleView(override val onTouchDown: (Puzzle, Int, Int) -> Unit,
                     override val onTouchDragged: (Puzzle, Int, Int) -> Unit,
                     override val onTouchUp: (Puzzle) -> Unit) : Puzzle() {
    override var tiles: List<List<MyImage>> = listOf(listOf())
    override var items: MutableList<MutableList<MyImage>> = mutableListOf(mutableListOf())
}