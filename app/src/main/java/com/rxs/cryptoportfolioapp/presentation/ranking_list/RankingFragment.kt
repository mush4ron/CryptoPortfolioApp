package com.rxs.cryptoportfolioapp.presentation.ranking_list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.databinding.FragmentRankingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private val viewModel: RankingViewModel by viewModels()

    @Inject
    lateinit var rankingAdapter: RankingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(layoutInflater)
        setupView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startObserve()
    }

    private fun startObserve() {
        viewModel.coinsData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                    binding.pbFragmentRanking.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    it.data?.let { it1 -> rankingAdapter.submitData(it1) }
                    binding.pbFragmentRanking.visibility = View.GONE
                    binding.rvFragmentRanking.visibility = View.VISIBLE
                }

                is Resource.Error -> {
                    binding.pbFragmentRanking.visibility = View.GONE
                    binding.tvFragmentRankingError.apply {
                        visibility = View.VISIBLE
                        text = it.message
                    }

                }
            }
        }
    }

    private fun setupView() {
        binding.rvFragmentRanking.apply {
            layoutManager =
                LinearLayoutManager(
                    this@RankingFragment.context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = rankingAdapter
        }
    }


}