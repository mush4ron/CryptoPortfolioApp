package com.rxs.cryptoportfolio.domain.repository

import com.rxs.cryptoportfolioapp.data.remote.dto.ListCoinDto

interface CoinRepository {
    suspend fun getCoins(): ListCoinDto
}