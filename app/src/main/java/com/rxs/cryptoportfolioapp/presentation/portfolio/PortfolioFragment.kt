package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.common.toRussianFormat
import com.rxs.cryptoportfolioapp.databinding.FragmentPortfolioBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class PortfolioFragment : Fragment() {

    private lateinit var binding: FragmentPortfolioBinding

    @Inject
    lateinit var viewModel: PortfolioViewModel

    private lateinit var portfolioAssetsAdapter: PortfolioAssetsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPortfolioBinding.inflate(layoutInflater)
        portfolioAssetsAdapter = PortfolioAssetsAdapter()
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPortfolio()
        startObserve()
    }

    private fun startObserve() {
        viewModel.portfolio.observe(viewLifecycleOwner) {
            binding.apply {
                tvFragmentPortfolioInvestedBalance.text =
                    it.balance.toRussianFormat() + " ₽"

                val usdtBalance = if (it.averageUsdt != 0.0) {
                    (((it.balance / it.averageUsdt) * 100.0).roundToInt() / 100.0)
                } else {
                    0.0
                }.toRussianFormat() + " USDT"
                tvFragmentPortfolioInvestedUsdtBalance.text = usdtBalance

                val rate = "1 USDT = ${it.averageUsdt.toRussianFormat()} ₽"
                tvFragmentPortfolioInvestedRate.text = rate

                if (it.assets.size > 0) {
                    tvFragmentPortfolioEmptyPortfolioTitle.visibility = View.GONE
                    rvFragmentPortfolioAssets.visibility = View.VISIBLE
                    recreateAdapter()
                    portfolioAssetsAdapter.submitData(it.assets)
                } else {
                    rvFragmentPortfolioAssets.visibility = View.GONE
                    tvFragmentPortfolioEmptyPortfolioTitle.visibility = View.VISIBLE
                }
            }

        }
    }

    private fun setupView() {
        binding.apply {
            btnFragmentPortfolioInvest.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_portfolioFragment_to_investDialogFragment
                ).onClick(it)
            }

            btnFragmentPortfolioWithdraw.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_portfolioFragment_to_withdrawDialogFragment
                ).onClick(it)
            }

            rvFragmentPortfolioAssets.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@PortfolioFragment.context,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = portfolioAssetsAdapter
            }
        }
    }

    private fun recreateAdapter() {
        portfolioAssetsAdapter = PortfolioAssetsAdapter()
        binding.rvFragmentPortfolioAssets.adapter = portfolioAssetsAdapter
    }
}

