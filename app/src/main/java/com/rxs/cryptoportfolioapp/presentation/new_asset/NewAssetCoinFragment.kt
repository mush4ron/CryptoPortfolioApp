package com.rxs.cryptoportfolioapp.presentation.new_asset

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.databinding.FragmentNewAssetCoinBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewAssetCoinFragment : Fragment() {

    private lateinit var binding: FragmentNewAssetCoinBinding

    @Inject
    lateinit var viewModel: NewAssetViewModel

    @Inject
    lateinit var coinAssetAdapter: CoinAssetAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewAssetCoinBinding.inflate(layoutInflater)
        setupView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserve()
    }

    private fun startObserve() {
        viewModel.coins.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbFragmentNewAssetCoin.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    it.data?.let { it1 -> coinAssetAdapter.submitData(it1) }
                    binding.pbFragmentNewAssetCoin.visibility = View.GONE
                    binding.rvFragmentNewAssetCoin.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.pbFragmentNewAssetCoin.visibility = View.GONE
                    binding.tvFragmentNewAssetCoinError.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }

                }
            }
        }
    }


    private fun setupView() {
        binding.rvFragmentNewAssetCoin.apply {
            layoutManager =
                LinearLayoutManager(
                    this@NewAssetCoinFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = coinAssetAdapter
        }
    }
}