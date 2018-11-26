package com.sniped.ui.parking.detail

import com.sniped.ui.BasePresenter
import com.sniped.ui.navigator.BackNavigator
import javax.inject.Inject

class ParkingPresenter @Inject constructor(
        private val view: IParking.View,
        private val backNavigator: BackNavigator) : BasePresenter() {

    fun onBackPressed() = backNavigator.navigate()
}
