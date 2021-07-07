package com.example.opnchallenge.screen.store.adapter.model

import com.example.data.model.Store
import com.example.domain.model.ProductViewState
import com.example.domain.model.StoreViewState

sealed class StoreItemModel(
    val viewId: String
) {
    data class StoreModel(
        val store: Store = Store()
    ) : StoreItemModel(
        viewId = "STORE_VIEW"
    )

    data class ProductListModel(
        val productList: List<ProductViewState>
    ) : StoreItemModel(
        viewId = "PRODUCT_LIST_VIEW"
    )

    companion object {
        fun fromStoreViewState(storeViewState: StoreViewState): List<StoreItemModel> {
            return listOf(
                StoreModel(
                    store = storeViewState.store
                ),
                ProductListModel(
                    productList = storeViewState.productList
                )
            )
        }
    }
}