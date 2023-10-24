package com.rxs.cryptoportfolioapp.presentation.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.usecase.GetPortfolioUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.SavePortfolioUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

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


    fun addBalance(value: Int, valueUsdt: Double) {
        val newBalance = _portfolio.value!!.balance.plus(value)
        val oldUsdtBalance = if (_portfolio.value!!.averageUsdt != 0.0) {
            _portfolio.value!!.balance / _portfolio.value!!.averageUsdt
        } else {
            0.0
        }
        val newAverageUsdt =
            (newBalance / (oldUsdtBalance + valueUsdt) * 100.0).roundToInt() / 100.0

        _portfolio.value =
            Portfolio(balance = newBalance, averageUsdt = newAverageUsdt)

        viewModelScope.launch {
            savePortfolioUseCase(data = _portfolio.value)
        }
    }

    fun withdrawBalance(value: Int) {
        val newBalance = _portfolio.value!!.balance.plus(value)
        val averageUsdt = _portfolio.value!!.averageUsdt

        _portfolio.value =
            Portfolio(balance = newBalance, averageUsdt = averageUsdt)

        viewModelScope.launch {
            savePortfolioUseCase(data = _portfolio.value)
        }
    }
}