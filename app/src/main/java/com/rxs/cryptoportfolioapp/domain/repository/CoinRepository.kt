package com.rxs.cryptoportfolio.domain.repository

import com.rxs.cryptoportfolio.data.remote.dto.ListCoinDto

interface CoinRepository {
    suspend fun getCoins(): ListCoinDto
}