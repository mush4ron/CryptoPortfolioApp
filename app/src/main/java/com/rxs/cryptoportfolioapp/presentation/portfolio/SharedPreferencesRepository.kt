package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.content.Context
import com.google.gson.Gson
import com.rxs.cryptoportfolioapp.common.Constants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val prefs = context.getSharedPreferences(Constants.SHARED_KEY, Context.MODE_PRIVATE)
    private val gson = Gson()

    fun save(value: Int) {
        with(prefs.edit()) {
            val json: String = gson.toJson(value)
            putString(Constants.BALANCE_KEY, json)
            apply()
        }
    }

    fun get(): Int {
        val json: String? = prefs.getString(Constants.BALANCE_KEY, null)
        return if (json != null) {
            gson.fromJson(json, Int::class.java)
        } else {
            0
        }
    }
}