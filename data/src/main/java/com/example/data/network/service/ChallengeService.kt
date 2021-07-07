package com.example.data.network.service

import com.example.data.model.Product
import com.example.data.model.Store
import com.example.data.model.request.OrderRequest
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChallengeService {
    @GET("storeInfo")
    fun fetchStoreInfo(): Single<Store>

    @GET("products")
    fun fetchProduct(): Single<List<Product>>

    @POST("order")
    fun makeOrder(@Body orderRequest: OrderRequest): Completable
}