package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolioapp.common.DispatcherProvider
import com.rxs.cryptoportfolioapp.domain.repository.DataRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPortfolioUseCase @Inject constructor(
    private val repository: DataRepository,
    private val dispatcherProvider: DispatcherProvider
) {

    suspend operator fun invoke(): Int {
        return withContext(dispatcherProvider.io) {
            repository.get()
        }
    }
}