package com.minesweeper.app.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.minesweeper.app.R
import com.minesweeper.app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvFragmentHost) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBar()

        binding.apply {
            bnvNavBar.setupWithNavController(navController)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupActionBar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.navStatisticsFragment, R.id.navMenuFragment, R.id.navSettingsFragment)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navGameFragment -> {
                    supportActionBar?.setShowHideAnimationEnabled(false)
                    supportActionBar?.hide()
                }
                else -> supportActionBar?.show()
            }
        }
    }
}
