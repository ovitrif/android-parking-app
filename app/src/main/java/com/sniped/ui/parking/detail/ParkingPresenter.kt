package com.sniped.ui.parking.detail

import com.sniped.ui.BasePresenter
import com.sniped.ui.navigator.BackNavigator
import com.sniped.ui.parking.domain.Parking
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
