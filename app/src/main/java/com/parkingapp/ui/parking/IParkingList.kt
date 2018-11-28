package com.parkingapp.ui.parking

import androidx.annotation.ColorInt
import com.parkingapp.ui.parking.domain.Parking

interface IParkingList {

    interface View {
        fun setParkingList(list: List<Parking>)
        fun setCounterValue(count: Int)
        fun setCounterColor(@ColorInt color: Int)
        fun showErrorView()
        fun hideErrorView()
        fun hideProgressView()
        fun hasItems(): Boolean
    }
}
