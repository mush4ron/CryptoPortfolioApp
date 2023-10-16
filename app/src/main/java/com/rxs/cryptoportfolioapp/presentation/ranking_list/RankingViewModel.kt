package com.rxs.cryptoportfolioapp.presentation.ranking_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolio.domain.model.Coin
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.domain.usecase.GetCoinsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _coinsData = MutableLiveData<Resource<List<Coin>>>()
    val coinsData: LiveData<Resource<List<Coin>>> = _coinsData

    init {
        getCoins()
    }

    private fun getCoins() {
        viewModelScope.launch(Dispatchers.IO) {
            getCoinsUseCase().collect{
                _coinsData.postValue(it)
            }
        }
    }
}