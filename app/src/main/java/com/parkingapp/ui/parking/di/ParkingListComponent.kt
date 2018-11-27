package com.parkingapp.ui.parking.di

import com.parkingapp.di.ActivityScope
import com.parkingapp.di.AppComponent
import com.parkingapp.ui.navigator.NavigatorModule
import com.parkingapp.ui.parking.ParkingListPresenter
import dagger.Component

@ActivityScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ParkingListModule::class, NavigatorModule::class])
interface ParkingListComponent {

    fun presenter(): ParkingListPresenter
}
