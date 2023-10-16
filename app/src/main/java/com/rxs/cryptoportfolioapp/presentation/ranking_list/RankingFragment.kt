package com.rxs.cryptoportfolioapp.presentation.ranking_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rxs.cryptoportfolioapp.databinding.FragmentRankingBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding
    private val viewModel: RankingViewModel by viewModels()

    @Inject
    lateinit var rankingListAdapter: RankingListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRankingBinding.inflate(layoutInflater)
        setupView()
        observeState()
        return binding.root
    }

    private fun observeState() {
        val state = viewModel.state.value
        rankingListAdapter.submitData(state.coins)
        if (state.error.isNotBlank()) {
            binding.tvFragmentRankingError.text = state.error
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
            adapter = rankingListAdapter
        }
    }


}