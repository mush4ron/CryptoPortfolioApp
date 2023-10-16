package com.rxs.cryptoportfolioapp.presentation.ranking_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RankingListState())
    val state: State<RankingListState> = _state

    init {
        getCoins()
    }

    private fun getCoins() {
        getCoinsUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RankingListState(coins = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = RankingListState(
                        error = result.message ?: "An unexpected error occured"
                    )
                }
                is Resource.Loading -> {
                    _state.value = RankingListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}