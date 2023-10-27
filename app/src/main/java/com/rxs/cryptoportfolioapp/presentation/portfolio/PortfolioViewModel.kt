package com.rxs.cryptoportfolioapp.presentation.portfolio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.usecase.GetPortfolioUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.PortfolioBalanceUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PortfolioViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val portfolioBalanceUseCase: PortfolioBalanceUseCase
) : ViewModel() {

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio

    init {
        getPortfolio()
    }

    fun investBalance(investedValue: Int, boughtUst: Double) {
        viewModelScope.launch {
            _portfolio.postValue(
                portfolioBalanceUseCase.investBalance(
                    investedValue = investedValue,
                    boughtUst = boughtUst
                )
            )
        }
    }

    fun withdrawBalance(withdrawValue: Int) {
        viewModelScope.launch {
            _portfolio.postValue(
                portfolioBalanceUseCase.withdrawBalance(
                    withdrawValue = withdrawValue
                )
            )
        }
    }

    fun getPortfolio() {
        viewModelScope.launch {
            val sharedPortfolioWithPrices = getPortfolioUseCase.getPortfolioWithCurrentPrices()
            _portfolio.postValue(sharedPortfolioWithPrices)
        }
    }
}