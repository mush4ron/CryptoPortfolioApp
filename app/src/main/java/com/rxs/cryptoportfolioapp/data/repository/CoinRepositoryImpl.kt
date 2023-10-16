package com.rxs.cryptoportfolioapp.data.repository

import com.rxs.cryptoportfolioapp.data.remote.dto.ListCoinDto
import com.rxs.cryptoportfolio.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.data.remote.CoinMarketCapApi
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val api: CoinMarketCapApi
) : CoinRepository {

    override suspend fun getCoins(): ListCoinDto {
        return api.getCoins()
    }
}