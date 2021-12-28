package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TableRow
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.minesweeper.app.R
import com.minesweeper.app.databinding.FragmentGameBinding

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
        setupBoardTable(navArgs.rows, navArgs.columns)
    }

    private fun setupBoardTable(rows: Int, columns: Int) {
        for (j in 0 until rows) {
            val row = layoutInflater.inflate(R.layout.board_row, null) as TableRow

            for (i in 0 until columns) {
                row.addView(layoutInflater.inflate(R.layout.board_tile, null) as ImageButton)
            }

            binding.tlBoard.addView(row)
        }
    }
}
