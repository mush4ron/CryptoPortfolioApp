package com.rxs.cryptoportfolioapp.di

import com.rxs.cryptoportfolio.domain.repository.CoinRepository
import com.rxs.cryptoportfolioapp.common.Constants
import com.rxs.cryptoportfolioapp.data.remote.CoinMarketCapApi
import com.rxs.cryptoportfolioapp.data.repository.CoinRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoinMarketCapApi(): CoinMarketCapApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinMarketCapApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api: CoinMarketCapApi): CoinRepository {
        return CoinRepositoryImpl(api = api)
    }
}