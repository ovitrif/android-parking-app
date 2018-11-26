package com.sniped.ui.parking.detail.di

import com.sniped.di.ActivityScope
import com.sniped.di.AppComponent
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.detail.ParkingPresenter
import dagger.Component

@ActivityScope
@Component(
        dependencies = [AppComponent::class],
        modules = [ParkingModule::class, NavigatorModule::class]
)
interface ParkingComponent {

    fun presenter(): ParkingPresenter
}
