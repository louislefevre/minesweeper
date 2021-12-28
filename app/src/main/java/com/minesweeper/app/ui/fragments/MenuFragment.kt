package com.minesweeper.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.minesweeper.app.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var binding: FragmentMenuBinding
    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupOnClickListeners()
    }

    private fun setupOnClickListeners() {
        binding.apply {
            btEasyDifficulty.setOnClickListener { navigateToGameFragment(8, 8, 10) }
            btNormalDifficulty.setOnClickListener { navigateToGameFragment(16, 16, 40) }
            btHardDifficulty.setOnClickListener { navigateToGameFragment(30, 16, 99) }
        }
    }

    private fun navigateToGameFragment(rows: Int, columns: Int, mines: Int) {
        val action = MenuFragmentDirections.actionMenuFragmentToGameFragment(rows, columns, mines)
        findNavController().navigate(action)
    }
}
