package com.example.opnchallenge.screen.store.adapter.view_holder

import androidx.recyclerview.widget.GridLayoutManager
import com.example.opnchallenge.base.BaseViewHolder
import com.example.opnchallenge.databinding.ListItemProductListBinding
import com.example.opnchallenge.screen.store.adapter.ProductAdapter
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel
import com.example.opnchallenge.screen.store.relay.ProductActionRelay
import com.jakewharton.rxrelay3.Relay

class StoreProductListViewHolder(
    binding: ListItemProductListBinding,
    productActionRelay: Relay<ProductActionRelay>
) :
    BaseViewHolder(binding) {
    private val productAdapter by lazy { ProductAdapter(productActionRelay = productActionRelay) }

    init {
        with(binding) {
            recyclerView.apply {
                adapter = productAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    override fun bind(itemData: Any, position: Int) {
        (itemData as? StoreItemModel.ProductListModel)?.run {
            productAdapter.submitList(productList)
        }
    }
}