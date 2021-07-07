package com.example.opnchallenge.screen.order_summary.adapter.view_holder

import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemOrderSummaryTotalBinding
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel

class OrderSummaryTotalViewHolder(
    val binding: ListItemOrderSummaryTotalBinding
) :
    BaseViewHolder(binding) {

    override fun bind(itemData: Any, position: Int) {
        with(itemData as OrderSummaryItemModel.TotalModel) {
            with(binding) {
                textViewPrice.text = price.toString()
            }
        }
    }
}