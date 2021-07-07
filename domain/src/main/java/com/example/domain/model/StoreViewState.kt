package com.example.domain.model

import com.example.data.model.Store

data class StoreViewState(
    val store: Store = Store(),
    val productList: List<ProductViewState> = emptyList()
)