package com.rxs.cryptoportfolioapp.presentation.new_asset

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.databinding.FragmentNewAssetBuyValueBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewAssetBuyValueFragment : Fragment() {

    private lateinit var binding: FragmentNewAssetBuyValueBinding

    @Inject
    lateinit var viewModel: NewAssetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAssetBuyValueBinding.inflate(layoutInflater)
        setupView()

        return binding.root
    }

    private fun setupView() {
        binding.apply {
            viewModel.portfolioCoin.value?.coin?.apply {
                tvFragmentNewAssetBuyValueCoinName.text = name
                tvFragmentNewAssetBuyValueCoinSymbol.text = symbol
                tvFragmentNewAssetBuyValueCaption1.text = symbol
            }
            etFragmentNewAssetBuyValuePricePerCoin.text =
                Editable.Factory.getInstance().newEditable(viewModel.getSelectedCoinPrice())
            tvFragmentNewAssetBuyValueBalance.text = viewModel.getBalance()

            ivFragmentNewAssetBuyValueBack.setOnClickListener {
                Navigation.createNavigateOnClickListener(
                    R.id.action_newAssetBuyValueFragment_to_newAssetCoinFragment
                ).onClick(it)
            }

            btnFragmentNewAssetBuyValueAddTrans.setOnClickListener {
                if (etFragmentNewAssetBuyValueCount.text.isNotBlank()
                    && etFragmentNewAssetBuyValuePricePerCoin.text.isNotBlank()
                ) {
                    viewModel.saveTrans(
                        value = etFragmentNewAssetBuyValueCount.text.toString().toDouble(),
                        investedPrice = etFragmentNewAssetBuyValuePricePerCoin.text.toString().toDouble()
                    )

                    Navigation.createNavigateOnClickListener(
                        R.id.action_newAssetBuyValueFragment_to_portfolioFragment
                    ).onClick(it)
                } else {
                    tvFragmentNewAssetBuyValueError.visibility = View.VISIBLE
                }
            }
        }
    }
}