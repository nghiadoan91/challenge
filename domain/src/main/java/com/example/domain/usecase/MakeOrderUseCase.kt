package com.example.domain.usecase

import com.example.data.model.Product
import com.example.data.model.request.OrderRequest
import com.example.data.network.repository.StoreRepository
import com.example.domain.model.ProductViewState
import io.reactivex.rxjava3.core.Completable
import javax.inject.Inject

class MakeOrderUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun execute(productList: List<ProductViewState>, address: String): Completable {
        return storeRepository.makeOrder(
            OrderRequest(
                products = productList.map { productViewState ->
                    Product(
                        name = productViewState.name,
                        price = productViewState.price,
                    )
                },
                address = address
            )
        )
    }
}