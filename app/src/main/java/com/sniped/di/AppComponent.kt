package com.sniped.di

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sniped.api.ApiService
import com.sniped.core.RxSchedulers
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(application: Application)
    fun inject(activity: Activity)
    fun inject(broadcastReceiver: BroadcastReceiver)
    fun inject(service: Service)

    val context: Context
    val gson: Gson
    val apiService: ApiService
    val rxSchedulers: RxSchedulers
    val sharedPreferences: SharedPreferences
}
