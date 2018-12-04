package com.parkingapp.ui.parking.detail.di

import com.parkingapp.ui.parking.detail.IParking
import com.parkingapp.ui.parking.domain.Parking
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
