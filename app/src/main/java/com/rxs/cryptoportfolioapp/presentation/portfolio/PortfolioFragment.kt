package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.btnFragmentPortfolioInvest.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_portfolioFragment_to_investDialogFragment)
        }
    }
}