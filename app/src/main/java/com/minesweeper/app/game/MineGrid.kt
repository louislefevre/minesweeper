package com.minesweeper.app.game

class MineGrid(private val size: Int) {

    val tiles: MutableList<Tile> = mutableListOf()

    init {
        for (i in 0 until size*size) {
            tiles.add(Tile(Tile.BLANK))
        }
    }
}
