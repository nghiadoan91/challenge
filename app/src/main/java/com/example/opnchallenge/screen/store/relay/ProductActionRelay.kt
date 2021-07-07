package com.example.opnchallenge.screen.store.relay

import com.example.domain.model.ProductViewState

sealed class ProductActionRelay(
    open val product: ProductViewState
) {
    data class Add(override val product: ProductViewState) : ProductActionRelay(product)
    data class Minus(override val product: ProductViewState) : ProductActionRelay(product)
}