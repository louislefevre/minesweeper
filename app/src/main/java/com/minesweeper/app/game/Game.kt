package com.minesweeper.app.game

class Game(private val rows: Int, private val columns: Int, private val mines: Int) {

    val grid = Grid(rows, columns)

    init {
        generateNewGrid()
    }

    fun generateNewGrid() {
        val totalMines = if (mines > grid.size) grid.size else mines
        var minesPlaced = 0

        while (minesPlaced < totalMines) {
            val y = (0..rows).random()
            val x = (0..columns).random()

            if (grid.tileAtOrNull(x, y)?.value == Tile.BLANK) {
                grid.setTile(x, y, Tile(Tile.BOMB))
                minesPlaced++
            }
        }

        for (x in 0 until columns) {
            for (y in 0 until rows) {
                if (grid.tileAt(x, y).value != Tile.BOMB) {
                    val adjTiles = grid.adjacentTiles(x, y)
                    var minesCount = 0

                    for (tile in adjTiles) {
                        if (tile.value == Tile.BOMB) {
                            minesCount++
                        }
                    }

                    if (minesCount > 0) {
                        grid.setTile(x, y, Tile(minesCount))
                    }
                }
            }
        }
    }
}
