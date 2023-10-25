package com.rxs.cryptoportfolioapp.presentation.new_asset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rxs.cryptoportfolioapp.R

class NewAssetSellValueFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new_asset_sell_value, container, false)
    }
}