package com.rxs.cryptoportfolioapp.domain.usecase

import com.rxs.cryptoportfolio.data.remote.dto.toCoinList
import com.rxs.cryptoportfolio.domain.model.Coin
import com.rxs.cryptoportfolio.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCoinsUseCase @Inject constructor(
    private val repository: CoinRepository
) {

    operator fun invoke(): Flow<Resource<List<Coin>>> = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCoins().toCoinList()
            emit(Resource.Success(data = coins))
        } catch (e: Exception) {
            emit(Resource.Error(message = e.message ?: "Exception"))
        }
    }
}