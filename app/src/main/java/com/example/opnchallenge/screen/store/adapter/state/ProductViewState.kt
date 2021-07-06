package com.example.opnchallenge.screen.store.adapter.state

data class ProductViewState(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val addedQty: Int = 0
)