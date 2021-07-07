package com.example.data.base

import com.example.data.network.service.ChallengeService
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.serjltt.moshi.adapters.SerializeOnlyNonEmpty
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

abstract class BaseRepositoryTest {
    internal val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val moshi = Moshi.Builder()
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnlyNonEmpty.ADAPTER_FACTORY)
        .build()

    internal val challengeService = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.createSynchronous())
        .build()
        .create(ChallengeService::class.java)
}