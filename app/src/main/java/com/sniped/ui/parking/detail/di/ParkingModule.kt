package com.sniped.ui.parking.detail.di

import com.sniped.ui.parking.detail.IParking
import com.sniped.ui.parking.domain.Parking
import dagger.Module
import dagger.Provides

@Module
class ParkingModule(
        private val view: IParking.View,
        private val parkingViewModel: Parking) {

    @Provides
    fun provideView() = view

    @Provides
    fun provideParking() = parkingViewModel
}
