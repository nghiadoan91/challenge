package com.example.data.network.repository

import com.example.data.model.Product
import com.example.data.network.service.ChallengeService
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val challengeService: ChallengeService
) {
    fun fetchProduct(): Single<List<Product>> {
        return challengeService.fetchProduct()
    }
}