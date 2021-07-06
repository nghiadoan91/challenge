package com.example.opnchallenge.di

import com.example.data.di.NetworkingModule.Companion.ENDPOINT
import com.example.opnchallenge.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Named(ENDPOINT)
    fun provideEndpoint() = BuildConfig.ENDPOINT
}