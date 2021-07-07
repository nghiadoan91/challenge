package com.example.domain.usecase

import com.example.data.model.request.OrderRequest
import com.example.data.network.repository.StoreRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test

class MakeOrderUseCaseTest {

    private lateinit var makeOrderUseCase: MakeOrderUseCase

    private val storeRepository = mock<StoreRepository>()

    @Before
    fun setUp() {
        makeOrderUseCase = MakeOrderUseCase(
            storeRepository = storeRepository
        )
    }

    @Test
    fun `should return complete when make order success`() {
        // Arrange
        given(storeRepository.makeOrder(any())).willReturn(
            Completable.complete()
        )
        // Act
        val actual = makeOrderUseCase.execute(
            address = "", productList = emptyList()
        )

        // Assert
        actual.test()
            .assertComplete()
        verify(storeRepository).makeOrder(orderRequest = OrderRequest())
    }

    @Test
    fun `should return error when make order fail`() {
        // Arrange
        val expectedResult = Exception()
        given(storeRepository.makeOrder(any())).willReturn(
            Completable.error(expectedResult)
        )
        // Act
        val actual = makeOrderUseCase.execute(
            address = "", productList = emptyList()
        )

        // Assert
        actual.test().assertError(expectedResult)
        verify(storeRepository).makeOrder(any())
    }
}