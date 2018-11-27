package com.sniped.ui.parking.di

import com.sniped.di.ActivityScope
import com.sniped.di.AppComponent
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.ParkingListPresenter
import dagger.Component

@ActivityScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ParkingListModule::class, NavigatorModule::class])
interface ParkingListComponent {

    fun presenter(): ParkingListPresenter
}
