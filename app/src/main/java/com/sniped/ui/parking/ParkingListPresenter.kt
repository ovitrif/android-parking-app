package com.sniped.ui.parking

import com.sniped.ui.BasePresenter
import com.sniped.ui.parking.detail.ParkingNavigator
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val parkingNavigator: ParkingNavigator) : BasePresenter() {
}
