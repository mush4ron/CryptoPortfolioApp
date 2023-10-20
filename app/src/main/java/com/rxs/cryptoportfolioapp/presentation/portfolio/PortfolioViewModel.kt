package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PortfolioViewModel @Inject constructor(
    private val prefsRepository: SharedPreferencesRepository
) : ViewModel() {

    private val _balance = MutableLiveData<Int>()
    val balance: LiveData<Int> = _balance

    init {
        initBalance()
    }

    private fun initBalance() {
        _balance.value = prefsRepository.get()
    }


    fun addBalance(addingValue: Int) {
        _balance.value?.plus(addingValue)?.let {
            prefsRepository.save(it)
            Log.d("VM", "Save $it")
        }
    }
}