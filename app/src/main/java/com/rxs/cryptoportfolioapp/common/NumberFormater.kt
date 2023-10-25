package com.rxs.cryptoportfolioapp.common

import java.text.NumberFormat
import java.util.Locale


fun Double.toNewAssetBalance(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    numberFormat.maximumFractionDigits =
        this.toString().substring(this.toString().indexOf(".") + 1).length
    return numberFormat.format(this)
}


fun Int.toRussianCurrencyPortfolioBalance(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} ₽"
}

fun Double.toRussianFormatAverageRate(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} ₽"
}

fun Double.toUsdtPortfolioBalance(): String {
    val numberFormat = NumberFormat.getNumberInstance(Locale("ru", "RU"))
    return "${numberFormat.format(this)} USDT"
}

fun Double.toRankingPrice(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    if ((this * 10000).toInt() <= 0) {
        numberFormat.maximumFractionDigits = 8
    } else if (this.toInt() <= 0) {
        numberFormat.maximumFractionDigits = 4
    }
    return numberFormat.format(this)
}

fun Double.toPricePerCoin(): String {
    val multipliers = listOf(1, 100, 10000, 1000000, 100000000)

    for (multiplier in multipliers) {
        val newValue = this * multiplier
        if (newValue.toInt() > 0) {
            return String.format("%.${multiplier.toString().length + 1}f", this)
        }
    }

    return ""
}