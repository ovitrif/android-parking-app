package com.parkingapp.ui.parking.di

import com.parkingapp.ui.parking.IParkingList
import dagger.Module
import dagger.Provides

@Module
class ParkingListModule(
        private val view: IParkingList.View) {

    @Provides
    fun provideView() = view
}
