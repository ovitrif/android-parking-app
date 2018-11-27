package com.parkingapp.ui.parking.detail

import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.navigator.BackNavigator
import com.parkingapp.ui.parking.domain.Parking
import javax.inject.Inject

class ParkingPresenter @Inject constructor(
        private val view: IParking.View,
        private val parkingViewModel: Parking,
        private val backNavigator: BackNavigator) : BasePresenter() {

    override fun onAttach() {
        view.setTitle(parkingViewModel.name)
    }

    fun onBackPressed() = backNavigator.navigate()
}
