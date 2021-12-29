package com.minesweeper.app.game

class Grid(private val rows: Int, private val columns: Int) {

    private val tiles: List<MutableList<Tile>> = List(rows) {
        MutableList(columns) { Tile(Tile.BLANK) }
    }

    val size = rows * columns

    fun setTile(x: Int, y: Int, tile: Tile) {
        tiles[y][x] = tile
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
}
