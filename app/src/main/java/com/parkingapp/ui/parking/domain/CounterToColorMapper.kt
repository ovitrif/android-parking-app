package com.parkingapp.ui.parking.domain

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import javax.inject.Inject

class CounterToColorMapper @Inject constructor() {

    fun map(count: Int): Int {
        val colorRatio = count.coerceAtMost(255) / 255F
        return ColorUtils.blendARGB(Color.RED, Color.GREEN, colorRatio)
    }
}
