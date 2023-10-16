package com.rxs.cryptoportfolioapp.presentation.ranking_list

import com.rxs.cryptoportfolio.domain.model.Coin

data class RankingListState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = emptyList(),
    val error: String = ""
)