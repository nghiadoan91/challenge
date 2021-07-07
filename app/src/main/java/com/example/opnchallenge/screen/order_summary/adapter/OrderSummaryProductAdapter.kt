package com.example.opnchallenge.screen.order_summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemOrderSummaryProductBinding
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryProductViewHolder
import com.example.domain.model.ProductViewState

class OrderSummaryProductAdapter() :
    ListAdapter<ProductViewState, BaseViewHolder>(object :
            DiffUtil.ItemCallback<ProductViewState>() {
            override fun areItemsTheSame(
                oldItem: ProductViewState,
                newItem: ProductViewState
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ProductViewState,
                newItem: ProductViewState
            ): Boolean {
                return oldItem == newItem
            }
        }) {

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return OrderSummaryProductViewHolder(
            binding = ListItemOrderSummaryProductBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}