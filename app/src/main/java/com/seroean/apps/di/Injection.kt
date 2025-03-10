package com.seroean.apps.di

import android.content.Context
import com.seroean.apps.data.local.repository.SeroeanRepository
import com.seroean.apps.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): SeroeanRepository {
        val apiService = ApiConfig.getApiService()
        return SeroeanRepository(apiService)
    }
}