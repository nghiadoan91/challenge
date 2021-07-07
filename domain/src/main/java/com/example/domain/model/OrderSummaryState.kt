package com.example.domain.model

data class OrderSummaryState(
    val productList: List<ProductViewState> = emptyList()
)