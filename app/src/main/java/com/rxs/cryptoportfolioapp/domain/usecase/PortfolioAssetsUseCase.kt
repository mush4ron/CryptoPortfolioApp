package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import javax.inject.Inject

class PortfolioAssetsUseCase @Inject constructor(
    private val dataRepository: DataRepository,
    private val coinRepository: CoinRepository,
    private val dispatcherProvider: DispatcherProvider
) {


}