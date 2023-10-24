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
import kotlin.math.roundToInt

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
            binding.apply {
                tvFragmentPortfolioInvestedBalance.text =
                    it.balance.toRussianCurrencyText()

                val usdtBalance = if (it.averageUsdt != 0.0) {
                    (((it.balance / it.averageUsdt) * 100.0).roundToInt() / 100.0)
                } else {
                    0.0
                }.toUsdtText()
                tvFragmentPortfolioInvestedUsdtBalance.text = usdtBalance

                val rate = "1 USDT = ${it.averageUsdt.toRussianCurrencyText()}"
                tvFragmentPortfolioInvestedRate.text = rate
            }

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

fun Int.toRussianCurrencyText(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} ₽"
}

fun Double.toRussianCurrencyText(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} ₽"
}

fun Double.toUsdtText(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} USDT"
}

