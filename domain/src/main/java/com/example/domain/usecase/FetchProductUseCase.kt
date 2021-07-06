package com.example.domain.usecase

import com.example.data.model.Product
import com.example.data.network.repository.ProductRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    fun execute(): Single<List<Product>> {
        return productRepository.fetchProduct()
    }
}