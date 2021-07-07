package com.example.domain.usecase

import com.example.data.model.Product
import com.example.data.network.repository.ProductRepository
import com.example.domain.model.ProductViewState
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import java.util.*

class FetchProductUseCaseTest {

    private lateinit var fetchProductUseCase: FetchProductUseCase

    private val productRepository = mock<ProductRepository>()

    @Before
    fun setUp() {
        fetchProductUseCase = FetchProductUseCase(
            productRepository = productRepository
        )
    }

    @Test
    fun `should fetch product when fetch success`() {
        // Arrange
        val origin = listOf(
            Product(), Product()
        )
        val expectedResult = listOf(
            ProductViewState(id = 0), ProductViewState(id = 1)
        )
        given(productRepository.fetchProduct()).willReturn(
            Single.just(origin)
        )
        // Act
        val actual = fetchProductUseCase.execute()

        // Assert
        actual.test()
            .assertValue(expectedResult)
        verify(productRepository).fetchProduct()
    }

    @Test
    fun `should return error when fetch fail`() {
        // Arrange
        val expectedResult = Exception()
        given(productRepository.fetchProduct()).willReturn(
            Single.error(expectedResult)
        )
        // Act
        val actual = fetchProductUseCase.execute()

        // Assert
        actual.test()
            .assertError(expectedResult)
        verify(productRepository).fetchProduct()
    }
}