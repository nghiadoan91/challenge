package com.example.opnchallenge.screen.store.adapter.state

import com.example.data.model.Store
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel

data class StoreViewState(
    val store: Store = Store(),
    val productList: List<ProductViewState> = emptyList()
) {
    fun toAdapterCollection(): List<StoreItemModel> {
        return listOf(
            StoreItemModel.StoreModel(
                store = store
            ),
            StoreItemModel.ProductListModel(
                productList = productList
            )
        )
    }
}