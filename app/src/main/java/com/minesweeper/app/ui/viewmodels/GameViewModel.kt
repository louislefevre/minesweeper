package com.minesweeper.app.ui.viewmodels

import androidx.lifecycle.*
import com.minesweeper.app.game.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel(
    private val rows: Int,
    private val columns: Int,
    private val mines: Int
) : ViewModel() {

    private lateinit var game: Game
    val grid get() = game.grid

    private val _gameGrid = MutableLiveData<Grid>()
    val gameGrid: LiveData<Grid> = _gameGrid

    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> = _gameState

    private val _gameMode = MutableLiveData<GameMode>()
    val gameMode: LiveData<GameMode> = _gameMode

    private val _timeElapsed = MutableLiveData(0L)
    val timeElapsed: LiveData<Long> = _timeElapsed

    private val _flagsRemaining = MutableLiveData(mines)
    val flagsRemaining: LiveData<Int> = _flagsRemaining

    init {
        startNewGame()
        updateTimeElapsed()
    }

    fun startNewGame() {
        game = Game(rows, columns, mines)
        updateGameState()
    }

    fun revealAllMines() {
        game.revealMineTiles()
        updateGameState()
    }

    fun handleTileClick(tile: Tile) {
        game.handleTileClick(tile)
        updateGameState()
    }

    fun handleTileLongClick(tile: Tile) {
        game.handleTileLongClick(tile)
        updateGameState()
    }

    fun toggleMode() {
        game.toggleMode()
        updateGameMode()
    }

    private fun updateGameState() {
        _gameGrid.value = game.grid

        val state = game.state
        if (_gameState.value != state) {
            _gameState.value = state
        }

        val flags = game.flagsRemaining
        if (_flagsRemaining.value != flags) {
            _flagsRemaining.value = flags
        }
    }

    private fun updateGameMode() {
        _gameMode.value = game.mode
    }

    private fun updateTimeElapsed() {
        viewModelScope.launch {
            // Coroutine is cancelled once view model host is destroyed
            while (true) {
                val pos = game.elapsedTime
                if (_timeElapsed.value != pos) {
                    _timeElapsed.postValue(pos)
                }
                delay(100L)
            }
        }
    }
}

class GameViewModelFactory(
    private val rows: Int,
    private val columns: Int,
    private val mines: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
            return GameViewModel(rows, columns, mines) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}