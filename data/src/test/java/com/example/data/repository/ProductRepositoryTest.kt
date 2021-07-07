package com.example.data.repository

import com.example.data.base.BaseRepositoryTest
import com.example.data.network.repository.ProductRepository
import com.example.data.util.Util.mockResponse
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import java.net.HttpURLConnection

class ProductRepositoryTest : BaseRepositoryTest() {

    private lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {

        productRepository = ProductRepository(
            challengeService = challengeService
        )
    }

    @Test
    fun `should return products when fetch success`() {
        // Arrange
        val mockResponse = mockResponse(
            fileName = "product.json",
            responseCode = HttpURLConnection.HTTP_OK
        )
        mockWebServer.enqueue(mockResponse)

        // Act
        val actual = productRepository.fetchProduct()

        // Assert
        actual.test()
            .assertValue { products ->
                products.size == 5
            }
    }

    @Test
    fun `should return exception when fetch fail`() {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(HttpURLConnection.HTTP_NOT_FOUND)
        )

        // Act
        val actual = productRepository.fetchProduct()

        // Assert
        actual.test()
            .assertNotComplete()
    }
}