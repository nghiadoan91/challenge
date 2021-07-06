package com.example.domain.usecase

import com.example.data.model.Store
import com.example.data.network.repository.StoreRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class FetchStoreUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    fun execute(): Single<Store> {
        return storeRepository.fetchStore()
    }
}