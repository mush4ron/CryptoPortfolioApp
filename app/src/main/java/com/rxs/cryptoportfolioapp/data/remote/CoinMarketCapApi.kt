package com.rxs.cryptoportfolioapp.data.remote

import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.data.remote.dto.ListCoinDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinMarketCapApi {
    @GET("/v1/cryptocurrency/listings/latest")
    suspend fun getCoins(
        @Query("CMC_PRO_API_KEY") token: String = Constants.TOKEN,
        @Query("limit") limit: String = Constants.COINS_LIMIT
    ): ListCoinDto
}