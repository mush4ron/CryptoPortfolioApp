package com.rxs.cryptoportfolioapp.data.shared_prefs

import com.rxs.cryptoportfolioapp.domain.model.Coin

data class PortfolioCoin (
    val coin: Coin? = null,
    var value: Double = 0.0,
    var investedPrice: Double? = null
)