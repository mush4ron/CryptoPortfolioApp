package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.data.remote.dto.ranking_list.toCoinList
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPortfolioUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val coinRepository: CoinRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getPortfolio(): Portfolio {
        return withContext(dispatcherProvider.io) {
            dataRepository.get()
        }
    }

    suspend fun getPortfolioWithCurrentPrices(): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val coinsData = coinRepository.getCoins().toCoinList()

            sharedPortfolio.assets.map { portfolioCoin ->
                portfolioCoin.coin?.symbol.let {
                    val currentPrice = coinsData.firstOrNull { coin ->
                        coin.symbol == (portfolioCoin.coin?.symbol)
                    }?.price ?: 0.0
                    val profitLoss =
                        (currentPrice - portfolioCoin.investedPrice) * portfolioCoin.value
                    portfolioCoin.apply {
                        this.currentPrice = currentPrice
                        this.profitLoss = profitLoss
                    }
                }

            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }
}