package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    @Inject
    lateinit var viewModel: PortfolioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserve()
    }

    private fun startObserve() {
        viewModel.portfolio.observe(viewLifecycleOwner) {
            binding.tvFragmentPortfolioInvestedBalance.text = it.balance.toBalanceText()
        }
    }

    private fun setupView() {
        binding.btnFragmentPortfolioInvest.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_portfolioFragment_to_investDialogFragment)
        }

        binding.btnFragmentPortfolioWithdraw.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_portfolioFragment_to_withdrawDialogFragment)
        }
    }
}

fun Int.toBalanceText(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} ₽"
}