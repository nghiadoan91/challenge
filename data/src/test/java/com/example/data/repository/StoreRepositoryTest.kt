package com.example.data.repository

import com.example.data.base.BaseRepositoryTest
import com.example.data.model.Store
import com.example.data.model.request.OrderRequest
import com.example.data.network.repository.StoreRepository
import com.example.data.util.Util.mockResponse
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class StoreRepositoryTest : BaseRepositoryTest() {

    private lateinit var storeRepository: StoreRepository

    @Before
    fun setUp() {

        storeRepository = StoreRepository(
            challengeService = challengeService
        )
    }

    @Test
    fun `should return store when fetch success`() {
        // Arrange
        val expectedResult = Store(
            name = "The Coffee Shop",
            rating = 4.5,
            openingTime = "10:00",
            closingTime = "20:00"
        )
        val mockResponse = mockResponse(
            fileName = "store.json",
            responseCode = HttpURLConnection.HTTP_OK
        )
        mockWebServer.enqueue(mockResponse)

        // Act
        val actual = storeRepository.fetchStore()

        // Assert
        actual.test()
            .assertValue(expectedResult)
    }

    @Test
    fun `should return exception when fetch fail`() {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        // Act
        val actual = storeRepository.fetchStore()

        // Assert
        actual.test()
            .assertNotComplete()
    }

    @Test
    fun `should return complete when make order success`() {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_CREATED)
        )

        // Act
        val actual = storeRepository.makeOrder(OrderRequest())

        // Assert
        actual.test()
            .assertComplete()
    }

    @Test
    fun `should return exception when make order fail`() {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        // Act
        val actual = storeRepository.makeOrder(OrderRequest())

        // Assert
        actual.test()
            .assertNotComplete()
    }
}