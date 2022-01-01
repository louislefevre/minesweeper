package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minesweeper.app.R
import com.minesweeper.app.adapters.GridAdapter
import com.minesweeper.app.databinding.FragmentGameBinding
import com.minesweeper.app.game.Game
import com.minesweeper.app.game.Tile
import java.util.concurrent.TimeUnit

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gridAdapter: GridAdapter
    private lateinit var game: Game
    private val navArgs: GameFragmentArgs by navArgs()

    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerThread: Runnable = object : Runnable {
        override fun run() {
            binding.tvTimer.text = formatTime(game.elapsedTime)
            binding.tvFlagsRemaining.text = game.flagsRemaining.toString()
            timerHandler.postDelayed(this, 100);
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        game = getNewGame()
        gridAdapter = GridAdapter(game.grid, { onTileClick(it) }, { onTileLongClick(it) })

        binding.apply {
            rvGrid.apply {
                layoutManager = GridLayoutManager(requireContext(), navArgs.columns)
                adapter = gridAdapter
            }

            ibReset.setOnClickListener { startNewGame() }
            ibToggleMode.setOnClickListener { toggleMode() }
        }

        timerHandler.postDelayed(timerThread, 100);
    }

    private fun getNewGame(): Game {
        return Game(navArgs.rows, navArgs.columns, navArgs.mines)
    }

    private fun startNewGame() {
        val startGame = {
            game = getNewGame()
            updateGameStatus()
            gridAdapter.itemsClickable = true
        }

        if (game.isGameOver || game.isGameWon) {
            startGame()
        } else if (game.isFirstMoveMade) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.restart_dialog_title)
                .setMessage(R.string.restart_dialog_body)
                .setNegativeButton(R.string.restart_dialog_decline, null)
                .setPositiveButton(R.string.restart_dialog_accept) { _, _ -> startGame() }
                .show()
        }
    }

    private fun toggleMode() {
        game.toggleMode()
        binding.ibToggleMode.setImageResource(
            if (game.isFlagMode) R.drawable.ic_flag
            else R.drawable.ic_mine
        )
    }

    private fun onTileClick(tile: Tile) {
        game.handleTileClick(tile)
        updateGameStatus()
    }

    private fun onTileLongClick(tile: Tile) {
        game.handleTileLongClick(tile)
        updateGameStatus()
    }

    private fun updateGameStatus() {
        if (game.isGameOver) {
            Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_SHORT).show()
            game.revealMineTiles()
            gridAdapter.itemsClickable = false
        } else if (game.isGameWon) {
            Toast.makeText(requireContext(), "Game Won", Toast.LENGTH_SHORT).show()
            game.revealMineTiles()
            gridAdapter.itemsClickable = false
        }
        gridAdapter.updateGrid(game.grid)
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds);
    }
}
