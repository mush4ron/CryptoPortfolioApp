package com.rxs.cryptoportfolioapp.presentation.ranking_list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rxs.cryptoportfolio.domain.model.Coin
import com.rxs.cryptoportfolioapp.databinding.ItemRankingListBinding
import javax.inject.Inject

class RankingListAdapter @Inject constructor() :
    RecyclerView.Adapter<RankingListAdapter.RankingListViewHolder>() {

    private lateinit var binding: ItemRankingListBinding
    private var coinsList = emptyList<Coin>()


    inner class RankingListViewHolder : RecyclerView.ViewHolder(binding.root) {

        fun setData(coin: Coin) = binding.apply {
            tvItemRankingListSymbol.text = coin.symbol
            tvItemRankingListPrice.text = coin.price.toString()
            tvItemRankingListChange24.text = "24.45%"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingListViewHolder {
        binding = ItemRankingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RankingListViewHolder()
    }

    override fun getItemCount(): Int {
        return coinsList.size
    }

    override fun onBindViewHolder(holder: RankingListViewHolder, position: Int) {
        holder.setData(coin = coinsList[position])
    }

    fun submitData(data: List<Coin>) {
        val newsDiffUtil = NewsDiffUtils(coinsList, data)
        val diffUtils = DiffUtil.calculateDiff(newsDiffUtil)
        coinsList = data
        diffUtils.dispatchUpdatesTo(this)
    }

    class NewsDiffUtils(
        private val oldItem: List<Coin>,
        private val newItem: List<Coin>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        override fun getNewListSize(): Int {
            return newItem.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }
    }
}