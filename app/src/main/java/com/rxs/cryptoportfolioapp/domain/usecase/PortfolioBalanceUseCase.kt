package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.data.shared_prefs.Portfolio
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.roundToInt

class PortfolioBalanceUseCase @Inject constructor(
    private val repository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend fun investBalance(investedValue: Int, boughtUst: Double): Portfolio {
        return withContext(dispatcherProvider.io) {
            val oldPortfolio = repository.get()
            val oldBalance = oldPortfolio.balance
            val oldAverageUsdt = oldPortfolio.averageUsdt
            val oldUsdtBalance = if (oldAverageUsdt != 0.0) (oldBalance / oldAverageUsdt) else 0.0

            val newBalance = oldPortfolio.balance.plus(investedValue)
            val newUsdtBalance = oldUsdtBalance.plus(boughtUst)
            val newAverageUsdt = ((newBalance / newUsdtBalance) * 100.0).roundToInt() / 100.0
            val newPortfolio = Portfolio(
                balance = newBalance,
                averageUsdt = newAverageUsdt,
                assets = oldPortfolio.assets
            )

            repository.save(data = newPortfolio)
            newPortfolio
        }
    }

    suspend fun withdrawBalance(withdrawValue: Int): Portfolio {
        return withContext(dispatcherProvider.io) {
            val oldPortfolio = repository.get()
            val newBalance = oldPortfolio.balance.minus(withdrawValue)
            val newPortfolio = Portfolio(
                balance = newBalance,
                averageUsdt = oldPortfolio.averageUsdt,
                assets = oldPortfolio.assets
            )

            repository.save(data = newPortfolio)
            newPortfolio
        }
    }
}