package com.parkingapp

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.multidex.MultiDex
import com.parkingapp.di.AppComponent
import com.parkingapp.di.AppModule
import com.parkingapp.di.DaggerAppComponent
import io.reactivex.plugins.RxJavaPlugins

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        init()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        installMultiDex()
    }

    protected open fun installMultiDex() {
        MultiDex.install(this)
    }

    protected open fun init() {
        initDagger()
        initRxJavaErrorHandler()
        attachDebugger()
    }

    private fun initDagger() {
        component = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        component.inject(this)
    }

    private fun initRxJavaErrorHandler() {
        RxJavaPlugins.setErrorHandler {
            // Not the best approach but good enough for a proof of concept
            Log.e("ERROR", it.message)
        }
    }

    private fun attachDebugger() {
        if (BuildConfig.DEBUG) Debugger.attachToApp(this)
    }

    companion object {
        lateinit var component: AppComponent
    }
}
