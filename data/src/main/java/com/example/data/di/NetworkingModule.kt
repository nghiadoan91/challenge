package com.example.data.di

import android.content.Context
import com.example.data.BuildConfig
import com.example.data.network.service.ChallengeService
import com.serjltt.moshi.adapters.DeserializeOnly
import com.serjltt.moshi.adapters.SerializeOnly
import com.serjltt.moshi.adapters.SerializeOnlyNonEmpty
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkingModule {
    companion object {
        @JvmStatic
        private val TAG = NetworkingModule::class.java.simpleName

        const val HTTP_CLIENT = "HTTP_CLIENT"
        const val RETROFIT = "RETROFIT"
        const val ENDPOINT = "ENDPOINT"
    }

    @Singleton
    @Provides
    fun provideMoshi() = Moshi.Builder()
        .add(DeserializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnly.ADAPTER_FACTORY)
        .add(SerializeOnlyNonEmpty.ADAPTER_FACTORY)
        .build()

    @Singleton
    @Provides
    fun provideNormalInterceptor() = HttpLoggingInterceptor().apply {
        setLevel(
            when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context) =
        Cache(File(context.cacheDir, "http"), 30 * 1024 * 1024)

    @Singleton
    @Provides
    fun provideRxJava3CallAdapterFactory() = RxJava3CallAdapterFactory.createSynchronous()

    @Named(HTTP_CLIENT)
    @Singleton
    @Provides
    fun provideHttpClient(
        cache: Cache,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .followRedirects(false)
            .cache(cache)
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
        return builder.build()
    }

    @Named(RETROFIT)
    @Singleton
    @Provides
    fun provideRetrofit(
        @Named(ENDPOINT) endpoint: String,
        moshi: Moshi,
        @Named(HTTP_CLIENT) okHttpClient: OkHttpClient,
        rxJava3CallAdapterFactory: RxJava3CallAdapterFactory
    ) =
        Retrofit.Builder()
            .baseUrl(endpoint)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(rxJava3CallAdapterFactory)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideChallengeService(@Named(RETROFIT) retrofit: Retrofit) = retrofit.create(ChallengeService::class.java)
}