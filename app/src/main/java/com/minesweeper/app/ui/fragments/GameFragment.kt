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

        // TODO: Replace parameters with navArgs values
        game = Game(10, 10, 10)
        gridAdapter = GridAdapter(game.grid, { onTileClick(it) }, { onTileLongClick(it) })

        binding.apply {
            rvBoard.adapter = gridAdapter
            ibReset.setOnClickListener { newGame() }
        }

        timerHandler.postDelayed(timerThread, 100);
    }

    private fun newGame() {
        game.generateNewGrid()
        updateGameStatus()
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
            game.revealAllTiles()
        } else if (game.isGameWon) {
            Toast.makeText(requireContext(), "Game Won", Toast.LENGTH_SHORT).show()
            game.revealAllTiles()
        }
        gridAdapter.updateGrid(game.grid)
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds);
    }
}
