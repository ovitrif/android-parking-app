package com.sniped.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sniped.R
import com.sniped.api.ApiService
import com.sniped.api.ApiServiceBuilder
import com.sniped.core.RxSchedulers
import com.sniped.core.config.AppConfig
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    @Singleton
    fun provideApiService(
            gson: Gson): ApiService {
        val apiUri = context.getString(R.string.api)
        val apiServiceBuilder = ApiServiceBuilder(
                apiUri,
                gson)

        return apiServiceBuilder
                .build()
                .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRxSchedulers() = RxSchedulers()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = context.getSharedPreferences(AppConfig.FILE_NAME_PREFS, Context.MODE_PRIVATE)
}
