package com.example.opnchallenge.screen.order_summary.adapter.view_holder

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemProductListBinding
import com.example.opnchallenge.screen.order_summary.adapter.OrderSummaryProductAdapter
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel

class OrderSummaryProductListViewHolder(
    binding: ListItemProductListBinding
) :
    BaseViewHolder(binding) {
    private val productAdapter by lazy { OrderSummaryProductAdapter() }

    init {
        with(binding) {
            recyclerView.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }

    override fun bind(itemData: Any, position: Int) {
        (itemData as? OrderSummaryItemModel.ProductListModel)?.run {
            productAdapter.submitList(productList)
        }
    }
}