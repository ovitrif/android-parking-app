package com.sniped.ui.parking.detail.di

import com.sniped.ui.parking.detail.IParking
import dagger.Module
import dagger.Provides

@Module
class ParkingModule(
        private val view: IParking.View) {

    @Provides
    fun provideView() = view
}
