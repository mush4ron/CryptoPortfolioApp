package com.rxs.cryptoportfolioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rxs.cryptoportfolioapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBottomMenu()
    }

    private fun setupBottomMenu() {
        supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.findNavController()
            ?.let { binding.bottomNavigationView.setupWithNavController(it) }
    }
}