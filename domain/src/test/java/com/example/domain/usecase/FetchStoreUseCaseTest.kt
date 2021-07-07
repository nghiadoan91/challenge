package com.example.domain.usecase

import com.example.data.model.Store
import com.example.data.network.repository.StoreRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import java.util.*
import kotlin.random.Random

class FetchStoreUseCaseTest {

    private lateinit var fetchStoreUseCase: FetchStoreUseCase

    private val storeRepository = mock<StoreRepository>()

    @Before
    fun setUp() {
        fetchStoreUseCase = FetchStoreUseCase(
            storeRepository = storeRepository
        )
    }

    @Test
    fun `should fetch store when fetch success`() {
        // Arrange
        val expectedResult = Store(
            name = UUID.randomUUID().toString(),
            rating = Random.nextDouble(),
            openingTime = UUID.randomUUID().toString(),
            closingTime = UUID.randomUUID().toString()
        )
        given(storeRepository.fetchStore()).willReturn(
            Single.just(expectedResult)
        )
        // Act
        val actual = fetchStoreUseCase.execute()

        // Assert
        actual.test()
            .assertValue(expectedResult)
        verify(storeRepository).fetchStore()
    }

    @Test
    fun `should return error when fetch fail`() {
        // Arrange
        val expectedResult = Exception()
        given(storeRepository.fetchStore()).willReturn(
            Single.error(expectedResult)
        )
        // Act
        val actual = fetchStoreUseCase.execute()

        // Assert
        actual.test()
            .assertError(expectedResult)
        verify(storeRepository).fetchStore()
    }
}