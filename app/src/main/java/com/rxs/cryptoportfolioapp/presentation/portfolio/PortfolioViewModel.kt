package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.domain.usecase.GetPortfolioUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.SavePortfolioUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase
) : ViewModel() {

    private val _balance = MutableLiveData<Int>()
    val balance: LiveData<Int> = _balance

    init {
        viewModelScope.launch {
            _balance.value = getPortfolioUseCase() ?: 0
        }
    }


    fun addBalance(addingValue: Int) {
        _balance.value?.plus(addingValue)?.let {
            _balance.value = it
            viewModelScope.launch {
                savePortfolioUseCase(it)
            }
        }
    }
}