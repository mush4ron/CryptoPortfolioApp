package com.rxs.cryptoportfolio.domain.model

data class Coin(
    val symbol: String,
    val price: Double,
    val percentChange24: Double
)