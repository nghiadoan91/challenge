package com.example.opnchallenge.screen.order_summary.adapter.model

import com.example.domain.model.OrderSummaryState
import com.example.domain.model.ProductViewState

sealed class OrderSummaryItemModel(
    val viewId: String
) {
    class HeaderModel : OrderSummaryItemModel(
        viewId = "HEADER_VIEW"
    )
    data class ProductListModel(
        val productList: List<ProductViewState>
    ) : OrderSummaryItemModel(
        viewId = "PRODUCT_LIST_VIEW"
    )

    data class TotalModel(
        val price: Double
    ) : OrderSummaryItemModel(
        viewId = "TOTAL_VIEW"
    )

    companion object {
        fun fromOrderSummaryState(orderSummaryState: OrderSummaryState): List<OrderSummaryItemModel> {
            return listOf(
                HeaderModel(),
                ProductListModel(orderSummaryState.productList),
                TotalModel(
                    price = orderSummaryState.productList.map { it.addedQty * it.price }
                        .sumByDouble { it }
                )
            )
        }
    }
}