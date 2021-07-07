package com.example.opnchallenge.store.adapter.model

import com.example.domain.model.StoreViewState
import com.example.opnchallenge.screen.store.adapter.model.StoreItemModel
import org.junit.Test
import kotlin.test.assertEquals

class StoreItemModelTest {

    @Test
    fun `should return list of 2 StoreItemModel when createFromStoreViewState`() {
        // Arrange
        val storeViewState = StoreViewState()

        // Act
        val actual = StoreItemModel.fromStoreViewState(
            storeViewState
        )

        // Assert
        assertEquals(2, actual.size)
    }
}