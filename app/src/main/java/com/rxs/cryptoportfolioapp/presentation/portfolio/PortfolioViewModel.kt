package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.data.sharedprefs.model.Portfolio
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

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio

    init {
        viewModelScope.launch {
            _portfolio.value = getPortfolioUseCase()!!
        }
    }


    fun addBalance(value: Int) {
        _portfolio.value =
            Portfolio(_portfolio.value!!.balance.plus(value), _portfolio.value!!.averageUsdt)

        viewModelScope.launch {
            savePortfolioUseCase(data = _portfolio.value)
        }
    }

    fun withdrawBalance(value: Int) {
        _portfolio.value =
            Portfolio(_portfolio.value!!.balance.minus(value), _portfolio.value!!.averageUsdt)

        viewModelScope.launch {
            savePortfolioUseCase(data = _portfolio.value)
        }
    }
}