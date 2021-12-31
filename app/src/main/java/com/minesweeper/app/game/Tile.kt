package com.minesweeper.app.game

import com.minesweeper.app.R

class Tile(var value: Int) {

    companion object {
        const val BOMB = -1
        const val BLANK = 0
    }

    var isFlagged = false
    var isRevealed = false

    fun getTileDrawable(): Int {
        return when (value) {
            BOMB -> R.drawable.ic_tile_mine
            BLANK -> R.drawable.ic_tile_0
            1 -> R.drawable.ic_tile_1
            2 -> R.drawable.ic_tile_2
            3 -> R.drawable.ic_tile_3
            4 -> R.drawable.ic_tile_4
            5 -> R.drawable.ic_tile_5
            6 -> R.drawable.ic_tile_6
            7 -> R.drawable.ic_tile_7
            8 -> R.drawable.ic_tile_8
            else -> R.drawable.ic_tile_hidden
        }
    }
}
