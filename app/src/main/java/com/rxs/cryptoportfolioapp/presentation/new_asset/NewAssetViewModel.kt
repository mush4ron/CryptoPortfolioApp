package com.rxs.cryptoportfolioapp.presentation.new_asset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rxs.cryptoportfolioapp.common.Resource
import com.rxs.cryptoportfolioapp.common.toNewAssetBalance
import com.rxs.cryptoportfolioapp.common.toPricePerCoin
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.data.shared_prefs.PortfolioCoin
import com.rxs.cryptoportfolioapp.domain.model.Coin
import com.rxs.cryptoportfolioapp.domain.usecase.AssetUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.GetCoinsUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.GetPortfolioUseCase
import com.rxs.cryptoportfolioapp.domain.usecase.SavePortfolioUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewAssetViewModel @Inject constructor(
    private val getPortfolioUseCase: GetPortfolioUseCase,
    private val savePortfolioUseCase: SavePortfolioUseCase,
    private val assetUseCase: AssetUseCase,
    private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {

    private val _portfolio = MutableLiveData<Portfolio>()
    val portfolio: LiveData<Portfolio> = _portfolio
    private val _coins = MutableLiveData<Resource<List<Coin>>>()
    val coins: LiveData<Resource<List<Coin>>> = _coins
    private val _newAssetCoin = MutableLiveData<PortfolioCoin>()
    val newAssetCoin: LiveData<PortfolioCoin> = _newAssetCoin

    init {
        getPortfolio()
        getCoins()
    }

    fun selectCoin(coin: Coin) {
        _newAssetCoin.value = PortfolioCoin(coin = coin)
    }

    fun saveTrans(value: Double, investedPrice: Double) {
        var contains = false
        val newAssets = _portfolio.value?.assets?.map {
            if (it.coin!!.name == _newAssetCoin.value?.coin?.name) {
                it.value?.plus(value)
                it.investedPrice?.plus(investedPrice)
                contains = true
            }
            it
        }?.toMutableList()

        if (!!contains) {
            newAssets?.add(
                PortfolioCoin(
                    coin = _newAssetCoin.value?.coin,
                    value = value,
                    investedPrice = investedPrice
                )
            )
        }

        _portfolio.value = Portfolio(
            balance = _portfolio.value!!.balance,
            averageUsdt = _portfolio.value!!.averageUsdt,
            assets = newAssets!!
        )
    }

    fun getSelectedCoinPrice(): String {
        val selectedDataCoin = _coinsData.value?.data?.filter {
            it.name == _newAssetCoin.value?.coin?.name
        }?.getOrNull(0)

        return selectedDataCoin?.price?.toPricePerCoin() ?: ""
    }

    fun getBalanceByCoinSymbol(): String {
        return assetUseCase.getBalanceByCoinSymbol(coinSymbol = _newAssetCoin.value!!.coin!!.symbol)
    }

    private fun getCoins() {
        viewModelScope.launch {
            getCoinsUseCase().collect {
                _coins.postValue(it)
            }
        }
    }

    private fun getPortfolio() {
        viewModelScope.launch {
            val sharedPortfolio = getPortfolioUseCase()
            _portfolio.postValue(sharedPortfolio)
        }
    }
}