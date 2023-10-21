package com.rxs.cryptoportfolioapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        applicationContext.getSharedPreferences(Constants.SHARED_KEY, 0).edit().clear().apply()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomMenu()
    }

    private fun setupBottomMenu() {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()
            ?.let { binding.bottomNavigationView.setupWithNavController(it) }
    }
}