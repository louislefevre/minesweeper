package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.minesweeper.app.databinding.FragmentGameBinding
import com.minesweeper.app.game.MineGridAdapter
import com.minesweeper.app.game.MinesweeperGame

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private val navArgs: GameFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val game = MinesweeperGame(10)
        binding.rvBoard.adapter = MineGridAdapter(game.mineGrid.tiles) {
            Toast.makeText(requireContext(), "Cell clicked", Toast.LENGTH_SHORT).show()
        }
    }
}
