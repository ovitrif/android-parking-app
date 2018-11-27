package com.parkingapp.ui.parking

import com.parkingapp.core.RxSchedulers
import com.parkingapp.extensions.subscribeEmpty
import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.parking.adapter.ParkingListAdapter
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.GetParkingList
import com.parkingapp.ui.parking.domain.Parking
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val view: IParkingList.View,
        private val getParkingList: GetParkingList,
        private val rxSchedulers: RxSchedulers,
        private val parkingNavigatorFactory: ParkingNavigatorFactory) : BasePresenter(), ParkingListAdapter.Listener {

    override fun onAttach() {
        fetchParkingStatus()
    }

    override fun onItemClick(item: Parking) = parkingNavigatorFactory.create(item).navigate()

    private fun fetchParkingStatus() {
        getParkingList.execute()
                .observeOn(rxSchedulers.ui)
                .doOnSuccess { view.setParkingList(it) }
                .subscribeEmpty()
    }
}
