package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.minesweeper.app.R
import com.minesweeper.app.adapters.GridAdapter
import com.minesweeper.app.databinding.FragmentGameBinding
import com.minesweeper.app.game.GameMode
import com.minesweeper.app.game.GameState
import com.minesweeper.app.ui.viewmodels.GameViewModel
import com.minesweeper.app.ui.viewmodels.GameViewModelFactory
import java.util.concurrent.TimeUnit

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gridAdapter: GridAdapter

    private val navArgs: GameFragmentArgs by navArgs()
    private val gameViewModel: GameViewModel by activityViewModels {
        GameViewModelFactory(navArgs.rows, navArgs.columns, navArgs.mines)
    }

    private var firstMoveMade = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gridAdapter = GridAdapter(
            gameViewModel.grid,
            { gameViewModel.handleTileClick(it) },
            { gameViewModel.handleTileLongClick(it) }
        )

        binding.apply {
            rvGrid.apply {
                layoutManager = GridLayoutManager(requireContext(), navArgs.columns)
                adapter = gridAdapter
            }

            ibReset.setOnClickListener {
                if (firstMoveMade) {
                    showRestartDialog()
                } else {
                    startNewGame()
                }
            }

            ibToggleMode.setOnClickListener { gameViewModel.toggleMode() }
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        gameViewModel.gameGrid.observe(viewLifecycleOwner) {
            gridAdapter.updateGrid(gameViewModel.grid)
        }

        gameViewModel.gameState.observe(viewLifecycleOwner) {
            when (it) {
                GameState.PLAYING -> firstMoveMade = true
                GameState.WON -> endGame("Game Won!")
                GameState.LOST -> endGame("Game Lost!")
                else -> Unit
            }
        }

        gameViewModel.gameMode.observe(viewLifecycleOwner) {
            binding.ibToggleMode.setImageResource(
                if (it == GameMode.FLAG_MODE) R.drawable.ic_flag
                else R.drawable.ic_mine
            )
        }

        gameViewModel.timeElapsed.observe(viewLifecycleOwner) {
            binding.tvTimer.text = formatTime(it)
        }

        gameViewModel.flagsRemaining.observe(viewLifecycleOwner) {
            binding.tvFlagsRemaining.text = formatFlags(it)
        }
    }

    private fun showRestartDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.restart_dialog_title)
            .setMessage(R.string.restart_dialog_body)
            .setNegativeButton(R.string.restart_dialog_decline, null)
            .setPositiveButton(R.string.restart_dialog_accept) { _, _ -> startNewGame() }
            .show()
    }

    private fun startNewGame() {
        gridAdapter.itemsClickable = true
        gameViewModel.startNewGame()
    }

    private fun endGame(text: String) {
        firstMoveMade = false
        gridAdapter.itemsClickable = false
        gameViewModel.revealAllMines()
        Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
    }

    private fun formatTime(millis: Long): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(minutes)
        return String.format("%02d:%02d", minutes, seconds);
    }

    private fun formatFlags(remainingFlags: Int): String {
        return String.format("%03d", remainingFlags)
    }
}
