package com.example.android.bookreader.di

import com.example.android.bookreader.api.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideApiService(): ApiService = ApiService()
}