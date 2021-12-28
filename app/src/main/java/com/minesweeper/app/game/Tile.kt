package com.minesweeper.app.game

class Tile(val value: Int) {

    companion object {
        val BOMB = -1
        val BLANK = 0
    }

    var isFlagged = false
    var isRevealed = false

}
