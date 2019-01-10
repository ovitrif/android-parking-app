package com.parkingapp.ui.parking.detail.di

import com.parkingapp.di.ActivityScope
import com.parkingapp.di.AppComponent
import com.parkingapp.ui.navigator.NavigatorModule
import com.parkingapp.ui.parking.detail.ParkingPresenter
import dagger.Component

@ActivityScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ParkingModule::class, NavigatorModule::class]
)
interface ParkingComponent {

    fun presenter(): ParkingPresenter
}
