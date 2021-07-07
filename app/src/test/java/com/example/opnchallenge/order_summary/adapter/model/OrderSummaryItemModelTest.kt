package com.example.opnchallenge.order_summary.adapter.model

import com.example.domain.model.OrderSummaryState
import com.example.opnchallenge.screen.order_summary.adapter.model.OrderSummaryItemModel
import org.junit.Test
import kotlin.test.assertEquals

class OrderSummaryItemModelTest {

    @Test
    fun `should return list of 3 StoreItemModel when createFromStoreViewState`() {
        // Arrange
        val orderSummaryState = OrderSummaryState()

        // Act
        val actual = OrderSummaryItemModel.fromOrderSummaryState(
            orderSummaryState
        )

        // Assert
        assertEquals(3, actual.size)
    }
}