package com.sniped.ui.parking.di

import com.sniped.ui.parking.IParkingList
import dagger.Module
import dagger.Provides

@Module
class ParkingListModule(
        private val view: IParkingList.View) {

    @Provides
    fun provideView() = view
}
