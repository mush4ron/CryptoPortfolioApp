package com.rxs.cryptoportfolio.data.remote.dto

import com.rxs.cryptoportfolio.domain.model.Coin

data class ListCoinDto(
    val data: List<Data>,
    val status: Status
)

fun ListCoinDto.toCoinList(): List<Coin> {
    val coinList = mutableListOf<Coin>()
    data.map {
        coinList.add(Coin(
            symbol = it.symbol,
            price = it.quote.USD.price
        ))
    }
    return coinList
}