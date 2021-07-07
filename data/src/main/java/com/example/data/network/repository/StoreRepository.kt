package com.example.data.network.repository

import com.example.data.model.Store
import com.example.data.model.request.OrderRequest
import com.example.data.network.service.ChallengeService
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val challengeService: ChallengeService
) {
    fun fetchStore(): Single<Store> {
        return challengeService.fetchStoreInfo()
    }

    fun makeOrder(orderRequest: OrderRequest): Completable {
        return challengeService.makeOrder(orderRequest)
    }
}