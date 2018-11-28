package com.parkingapp.ui.parking.di

import androidx.fragment.app.FragmentActivity
import com.parkingapp.ui.parking.IParkingList
import com.tbruyelle.rxpermissions2.RxPermissions
import dagger.Module
import dagger.Provides

@Module
class ParkingListModule(
        private val activity: FragmentActivity,
        private val view: IParkingList.View) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideRxPermissions() = RxPermissions(activity)
}
