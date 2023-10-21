package com.rxs.cryptoportfolioapp.domain.repository

interface DataRepository {

    suspend fun save(value: Int)

    suspend fun get(): Int
}