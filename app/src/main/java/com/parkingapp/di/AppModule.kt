package com.parkingapp.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.parkingapp.R
import com.parkingapp.api.ApiService
import com.parkingapp.api.ApiServiceBuilder
import com.parkingapp.core.RxSchedulers
import com.parkingapp.core.config.AppConfig
import com.patloew.rxlocation.RxLocation
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
    fun provideRxLocation() = RxLocation(context)

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences = context.getSharedPreferences(AppConfig.FILE_NAME_PREFS, Context.MODE_PRIVATE)
}
