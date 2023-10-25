package com.rxs.cryptoportfolioapp.data.shared_prefs

data class Portfolio(
    val balance: Int = 0,
    val averageUsdt: Double = 0.0,
    val assets: MutableList<PortfolioCoin> = mutableListOf()
)