package com.rxs.cryptoportfolioapp.data.remote

import com.rxs.cryptoportfolio.data.remote.dto.ListCoinDto
import retrofit2.http.GET

interface CoinMarketCapApi {
    // https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=8d204758-824f-4aff-8b14-0bc35f21c9c3&limit=200
    @GET("/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=8d204758-824f-4aff-8b14-0bc35f21c9c3&limit=200")
    suspend fun getCoins(): ListCoinDto
}