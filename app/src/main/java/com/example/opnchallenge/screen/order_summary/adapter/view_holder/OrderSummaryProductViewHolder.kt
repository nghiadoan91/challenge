package com.example.opnchallenge.screen.order_summary.adapter.view_holder

import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemOrderSummaryProductBinding
import com.example.domain.model.ProductViewState

class OrderSummaryProductViewHolder(
    val binding: ListItemOrderSummaryProductBinding
) : BaseViewHolder(binding) {
    override fun bind(itemData: Any, position: Int) {
        with(itemData as ProductViewState) {
            with(binding) {
                textViewName.text = "$name X $addedQty"
                textViewPrice.text = (price * addedQty).toString()
            }
        }
    }
}