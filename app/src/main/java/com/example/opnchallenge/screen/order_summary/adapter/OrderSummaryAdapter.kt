package com.example.opnchallenge.screen.order_summary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemOrderSummaryHeaderBinding
import com.example.opnchallenge.databinding.ListItemOrderSummaryTotalBinding
import com.example.opnchallenge.databinding.ListItemProductListBinding
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryHeaderViewHolder
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryProductListViewHolder
import com.example.opnchallenge.screen.order_summary.adapter.view_holder.OrderSummaryTotalViewHolder
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxrelay3.PublishRelay

class OrderSummaryAdapter :
    ListAdapter<OrderSummaryItemModel, BaseViewHolder>(object :
            DiffUtil.ItemCallback<OrderSummaryItemModel>() {
            override fun areItemsTheSame(
                oldItem: OrderSummaryItemModel,
                newItem: OrderSummaryItemModel
            ): Boolean {
                return oldItem.viewId == newItem.viewId
            }

            override fun areContentsTheSame(
                oldItem: OrderSummaryItemModel,
                newItem: OrderSummaryItemModel
            ): Boolean {
                return oldItem == newItem
            }
        }) {

    private val productActionRelay by lazy { PublishRelay.create<ProductActionRelay>() }

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_PRODUCT = 1
        private const val TYPE_TOTAL = 3
    }

    override fun getItemCount(): Int = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            TYPE_HEADER -> OrderSummaryHeaderViewHolder(
                ListItemOrderSummaryHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            TYPE_PRODUCT -> OrderSummaryProductListViewHolder(
                ListItemProductListBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ),
                    parent, false
                )
            )
            TYPE_TOTAL -> OrderSummaryTotalViewHolder(
                ListItemOrderSummaryTotalBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        currentList.getOrNull(position)?.let {
            return when (it) {
                is OrderSummaryItemModel.HeaderModel -> TYPE_HEADER
                is OrderSummaryItemModel.ProductListModel -> TYPE_PRODUCT
                is OrderSummaryItemModel.TotalModel -> TYPE_TOTAL
            }
        }
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(currentList[position], position)
    }
}