package com.rxs.cryptoportfolioapp.presentation.portfolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rxs.cryptoportfolioapp.R
import com.rxs.cryptoportfolioapp.common.toRussianFormat
import com.rxs.cryptoportfolioapp.data.shared_prefs.PortfolioCoin
import com.rxs.cryptoportfolioapp.databinding.ItemPortfolioAssetBinding

class PortfolioAssetsAdapter :
    RecyclerView.Adapter<PortfolioAssetsAdapter.PortfolioAssetViewHolder>() {

    private lateinit var binding: ItemPortfolioAssetBinding
    private var portfolioCoinsList = emptyList<PortfolioCoin>()


    inner class PortfolioAssetViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setData(portfolioCoin: PortfolioCoin, position: Int) = binding.apply {
            portfolioCoin.apply {
                tvItemPortfolioAssetPosition.text = (position + 1).toString()
                tvItemPortfolioAssetSymbol.text = coin?.symbol ?: ""
                tvItemPortfolioAssetValue.text = value.toString()
                tvItemPortfolioAssetInvestedPrice.text =
                    "${(investedPrice * value).toRussianFormat()} $"
                tvItemPortfolioAssetCurrentPrice.text =
                    "${(currentPrice * value).toRussianFormat()} $"
                if (profitLoss >= 0) {
                    tvItemPortfolioAssetDifferenceBad.visibility = View.GONE
                    tvItemPortfolioAssetDifferenceGood.text = "${profitLoss.toRussianFormat()} $"
                    tvItemPortfolioAssetDifferenceGood.visibility = View.VISIBLE
                } else {
                    tvItemPortfolioAssetDifferenceGood.visibility = View.GONE
                    tvItemPortfolioAssetDifferenceBad.text = "${profitLoss.toRussianFormat()} $"
                    tvItemPortfolioAssetDifferenceBad.visibility = View.VISIBLE
                }

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioAssetViewHolder {
        binding =
            ItemPortfolioAssetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PortfolioAssetViewHolder()
    }

    override fun getItemCount(): Int {
        return portfolioCoinsList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: PortfolioAssetViewHolder, position: Int) {
        holder.setData(portfolioCoin = portfolioCoinsList[position], position = position)
    }

    fun submitData(data: List<PortfolioCoin>) {
        portfolioCoinsList = data
    }
}