package com.example.domain.usecase

import com.example.data.network.repository.ProductRepository
import com.example.domain.model.ProductViewState
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {
    fun execute(): Single<List<ProductViewState>> {
        return productRepository.fetchProduct()
            .map {
                it.mapIndexed { index, product ->
                    ProductViewState(
                        id = index,
                        name = product.name,
                        price = product.price,
                        imageUrl = product.imageUrl
                    )
                }
            }
    }
}