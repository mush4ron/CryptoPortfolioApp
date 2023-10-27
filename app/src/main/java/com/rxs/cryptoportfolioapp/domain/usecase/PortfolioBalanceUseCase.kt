package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class PortfolioBalanceUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun investBalance(investedValue: Int, boughtUst: Double): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val oldBalance = sharedPortfolio.balance
            val oldAverageUsdt = sharedPortfolio.averageUsdt
            val oldUsdtBalance = if (oldAverageUsdt != 0.0) (oldBalance / oldAverageUsdt) else 0.0

            val newBalance = sharedPortfolio.balance.plus(investedValue)
            val newUsdtBalance = oldUsdtBalance.plus(boughtUst)
            val newAverageUsdt = ((newBalance / newUsdtBalance) * 100.0).roundToInt() / 100.0

            sharedPortfolio.apply {
                balance = newBalance
                averageUsdt = newAverageUsdt
            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }

    suspend fun withdrawBalance(withdrawValue: Int): Portfolio {
        return withContext(dispatcherProvider.io) {
            val sharedPortfolio = dataRepository.get()
            val newBalance = sharedPortfolio.balance.minus(withdrawValue)

            sharedPortfolio.apply {
                balance = newBalance
            }

            dataRepository.save(data = sharedPortfolio)
            sharedPortfolio
        }
    }
}