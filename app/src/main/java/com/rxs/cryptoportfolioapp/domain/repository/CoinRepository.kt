package com.rxs.cryptoportfolioapp.domain.repository

import com.rxs.cryptoportfolioapp.data.remote.dto.ListCoinDto

interface CoinRepository {
    suspend fun getCoins(): ListCoinDto
}