package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.common.toNewAssetBalance
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AssetUseCase @Inject constructor(
    private val repository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun getBalanceByCoinSymbol(coinSymbol: String): String {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = repository.get()
            val foundCoin = sharedPortfolio.assets.firstOrNull { it.coin?.symbol == coinSymbol }

            val result = foundCoin?.value?.toNewAssetBalance().plus(" $coinSymbol") ?: "0 $coinSymbol"
            result
        }
    }
}