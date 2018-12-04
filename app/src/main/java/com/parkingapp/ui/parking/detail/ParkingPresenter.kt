package com.parkingapp.ui.parking.detail

import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.navigator.BackNavigator
import com.parkingapp.ui.parking.detail.domain.StaticMapUrlGenerator
import com.parkingapp.ui.parking.domain.Parking
import javax.inject.Inject

class ParkingPresenter @Inject constructor(
        private val view: IParking.View,
        private val parkingViewModel: Parking,
        private val staticMapUrlGenerator: StaticMapUrlGenerator,
        private val backNavigator: BackNavigator) : BasePresenter() {

    override fun onAttach() {
        view.showParkingName(parkingViewModel.name)
        view.showContactDetails(parkingViewModel.contactInfo)
        view.showAddress(parkingViewModel.address)

        val staticMapUrl = staticMapUrlGenerator.fromLatLng(parkingViewModel.latLng)
        if (staticMapUrl.isNotEmpty()) {
            view.showMap()
            view.setMapUrl(staticMapUrl)
        }
    }

    fun onBackPressed() = backNavigator.navigate()
}
