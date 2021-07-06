package com.example.data.network.repository

import com.example.data.model.Store
import com.example.data.network.service.ChallengeService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val challengeService: ChallengeService
) {
    fun fetchStore(): Single<Store> {
        return challengeService.fetchStoreInfo()
    }
}