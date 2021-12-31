package com.minesweeper.app.game

class Grid(private val rows: Int, private val columns: Int) {

    private var tiles = emptyGrid()
    val size = rows * columns

    fun setTile(x: Int, y: Int, tile: Tile) {
        tiles[y][x] = tile
    }

    fun updateTile(x: Int, y: Int, newValue: Int) {
        tiles[y][x].value = newValue
    }

    fun tileAt(x: Int, y: Int): Tile {
        return tiles[y][x]
    }

    fun tileAt(index: Int): Tile {
        val x = index % columns
        val y = index / columns
        return tileAt(x, y)
    }

    fun tileAtOrNull(x: Int, y: Int): Tile? {
        if (x < 0 || x >= columns || y < 0 || y >= rows) {
            return null
        }
        return tileAt(x, y)
    }

    fun indexOfTile(tile: Tile): Int {
        val (x, y) = cordsOfTile(tile)
        return columns * y + x
    }

    fun cordsOfTile(tile: Tile): Pair<Int, Int> {
        tiles.forEachIndexed { y, list ->
            if (list.contains(tile)) {
                val x = list.indexOf(tile)
                return Pair(x, y)
            }
        }
        return Pair(-1, -1)
    }

    fun adjacentTiles(x: Int, y: Int): List<Tile> {
        return listOfNotNull(
            tileAtOrNull(x - 1, y),
            tileAtOrNull(x + 1, y),
            tileAtOrNull(x - 1, y - 1),
            tileAtOrNull(x, y - 1),
            tileAtOrNull(x + 1, y - 1),
            tileAtOrNull(x - 1, y + 1),
            tileAtOrNull(x, y + 1),
            tileAtOrNull(x + 1, y + 1),
        )
    }

    fun adjacentTiles(tile: Tile): List<Tile> {
        val (x, y) = cordsOfTile(tile)
        return adjacentTiles(x, y)
    }

    fun allTiles(): List<Tile> {
        val all = mutableListOf<Tile>()
        tiles.forEach {
            it.forEach { tile ->
                all.add(tile)
            }
        }
        return all
    }

    fun clearTiles() {
        tiles.clear()
        tiles = emptyGrid()
    }

    private fun emptyGrid(): MutableList<MutableList<Tile>> {
        return MutableList(rows) {
            MutableList(columns) { Tile(Tile.BLANK) }
        }
    }
}
