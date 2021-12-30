package com.minesweeper.app.game

import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit

class Game(private val rows: Int, private val columns: Int, private val mines: Int) {

    private val timer = Stopwatch.createUnstarted()

    val grid = Grid(rows, columns)
    val elapsedTime
        get() = timer.elapsed(TimeUnit.MILLISECONDS)
    val isGameWon
        get() = gridIsRevealed()
    var isGameOver = false
        private set
    var isClearMode = true
        private set

    init {
        generateGrid()
    }

    fun generateNewGrid() {
        isGameOver = false
        isClearMode = true
        timer.reset()
        grid.clearTiles()
        generateGrid()
    }

    fun handleTileClick(tile: Tile) {
        if (!isGameOver && !isGameWon && isClearMode) {
            if (!timer.isRunning) {
                timer.start()
            }

            clearTile(tile)
        }
    }

    fun revealAllTiles() {
        timer.stop()
        grid.allTiles().forEach {
            it.isRevealed = true
        }
    }

    private fun generateGrid() {
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

    private fun clearTile(tile: Tile) {
        tile.isRevealed = true

        if (tile.value == Tile.BOMB) {
            isGameOver = true
        } else if (tile.value == Tile.BLANK) {
            val toClear = mutableListOf<Tile>()
            val toCheckAdj = mutableListOf(tile)

            while (toCheckAdj.size > 0) {
                val nextTile = toCheckAdj[0]

                for (adjTile in grid.adjacentTiles(nextTile)) {
                    if (adjTile.value == Tile.BLANK) {
                        if (!toClear.contains(adjTile) && !toCheckAdj.contains(adjTile)) {
                            toCheckAdj.add(adjTile)
                        }
                    } else {
                        if (!toClear.contains(adjTile)) {
                            toClear.add(adjTile)
                        }
                    }
                }

                toCheckAdj.remove(nextTile)
                toClear.add(nextTile)
            }

            toClear.forEach { it.isRevealed = true }
        }
    }

    private fun gridIsRevealed(): Boolean {
        return grid.allTiles().none {
            it.value != Tile.BOMB && it.value != Tile.BLANK && !it.isRevealed
        }
    }
}
