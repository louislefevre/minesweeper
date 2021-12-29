package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.minesweeper.app.adapters.GridAdapter
import com.minesweeper.app.databinding.FragmentGameBinding
import com.minesweeper.app.game.Game

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var gridAdapter: GridAdapter
    private lateinit var game: Game
    private val navArgs: GameFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Replace parameters with navArgs values
        game = Game(10, 10, 10)
        gridAdapter = GridAdapter(game.grid) {
            onTileClick()
        }

        binding.apply {
            rvBoard.adapter = gridAdapter
            ibReset.setOnClickListener { newGame() }
        }
    }

    private fun newGame() {
        game.generateNewGrid()
        gridAdapter.setTiles(game.grid)
    }

    private fun onTileClick() {
        Toast.makeText(requireContext(), "Cell clicked", Toast.LENGTH_SHORT).show()
    }
}
