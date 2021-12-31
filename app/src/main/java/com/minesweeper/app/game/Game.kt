package com.minesweeper.app.game

import com.google.common.base.Stopwatch
import java.util.concurrent.TimeUnit

class Game(private val rows: Int, private val columns: Int, private val mines: Int) {

    private val timer = Stopwatch.createUnstarted()
    val elapsedTime
        get() = timer.elapsed(TimeUnit.MILLISECONDS)

    val grid = Grid(rows, columns)

    var flagsRemaining = mines

    val isGameWon
        get() = gridIsRevealed()
    var isGameOver = false
        private set
    var isClearMode = true
        private set
    var isFlagMode = false
        private set
    var isFirstMoveMade = false
        private set

    fun handleTileClick(tile: Tile) {
        if (!isGameOver && !isGameWon) {
            if (!timer.isRunning) {
                timer.start()
            }

            if (isClearMode && !tile.isFlagged) {
                if (!isFirstMoveMade) {
                    generateGrid(safeTile = tile)
                    isFirstMoveMade = true
                }
                clearTile(tile)
            } else if (isFlagMode) {
                toggleTileFlag(tile)
            }
        }
    }

    fun handleTileLongClick(tile: Tile) {
        toggleMode()
        handleTileClick(tile)
        toggleMode()
    }

    fun revealMineTiles() {
        timer.stop()
        grid.allTiles().filter {
            it.isMine
        }.forEach {
            it.isRevealed = true
        }
    }

    fun toggleMode() {
        isClearMode = !isClearMode
        isFlagMode = !isFlagMode
    }

    private fun generateGrid(safeTile: Tile? = null) {
        val totalMines = if (mines >= grid.size) grid.size - 1 else mines
        var minesPlaced = 0

        while (minesPlaced < totalMines) {
            val y = (0..rows).random()
            val x = (0..columns).random()
            val tile = grid.tileAtOrNull(x, y)

            if (tile?.isBlank == true) {
                if (safeTile != null && safeTile === tile)
                    continue

                grid.updateTile(x, y, Tile.MINE)
                minesPlaced++
            }
        }

        for (x in 0 until columns) {
            for (y in 0 until rows) {
                if (!grid.tileAt(x, y).isMine) {
                    val adjTiles = grid.adjacentTiles(x, y)
                    var minesCount = 0

                    for (tile in adjTiles) {
                        if (tile.isMine) {
                            minesCount++
                        }
                    }

                    if (minesCount > 0) {
                        grid.updateTile(x, y, minesCount)
                    }
                }
            }
        }
    }

    private fun clearTile(tile: Tile) {
        tile.isRevealed = true

        if (tile.isMine) {
            tile.isDetonated = true
            isGameOver = true
        } else if (tile.isBlank) {
            val toClear = mutableListOf<Tile>()
            val toCheckAdj = mutableListOf(tile)

            while (toCheckAdj.size > 0) {
                val nextTile = toCheckAdj[0]

                for (adjTile in grid.adjacentTiles(nextTile)) {
                    if (adjTile.isBlank) {
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

            toClear.forEach {
                if (!it.isFlagged) {
                    it.isRevealed = true
                }
            }
        }
    }

    private fun toggleTileFlag(tile: Tile) {
        if (!tile.isRevealed) {
            if (tile.isFlagged) {
                tile.isFlagged = false
                flagsRemaining++
            } else if (flagsRemaining > 0) {
                tile.isFlagged = true
                flagsRemaining--
            }
        }
    }

    private fun gridIsRevealed(): Boolean {
        return isFirstMoveMade && grid.allTiles().none {
            !it.isMine && !it.isBlank && !it.isRevealed
        }
    }
}
