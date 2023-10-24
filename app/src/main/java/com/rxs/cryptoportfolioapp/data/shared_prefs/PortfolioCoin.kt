package com.rxs.cryptoportfolioapp.data.shared_prefs

import com.rxs.cryptoportfolioapp.domain.model.Coin

data class PortfolioCoin (
    val Coin: Coin,
    val value: Double,
    val investedPrice: Double
)