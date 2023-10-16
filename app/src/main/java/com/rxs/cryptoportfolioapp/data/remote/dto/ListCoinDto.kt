package com.rxs.cryptoportfolioapp.data.remote.dto

import com.rxs.cryptoportfolio.domain.model.Coin

data class ListCoinDto(
    val data: List<Data>,
    val status: Status
)

fun ListCoinDto.toCoinList(): List<Coin> {
    val coinList = mutableListOf<Coin>()
    data.map {
        coinList.add(
            Coin(
                symbol = it.symbol,
                price = it.quote.USD.price,
                percentChange24 = (it.quote.USD.percent_change_24h * 100.0).toInt() / 100.0
            )
        )
    }
    return coinList
}