package com.rxs.cryptoportfolioapp.data.shared_prefs

data class Portfolio(
    var balance: Int = 0,
    var averageUsdt: Double = 0.0,
    var assets: MutableList<PortfolioCoin> = mutableListOf()
)