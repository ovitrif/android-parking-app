package com.parkingapp.core.config

import com.parkingapp.BuildConfig

object AppConfig {

    const val FILE_NAME_PREFS = BuildConfig.APPLICATION_ID
    const val SERVER_TIMEOUT_READ: Long = 30
    const val SERVER_TIMEOUT_WRITE: Long = 30
}
