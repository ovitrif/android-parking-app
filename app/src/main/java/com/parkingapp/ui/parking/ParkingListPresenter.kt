package com.parkingapp.ui.parking

import com.parkingapp.api.ApiService
import com.parkingapp.core.RxSchedulers
import com.parkingapp.extensions.subscribeEmpty
import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.parking.adapter.ParkingListAdapter
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.Parking
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val view: IParkingList.View,
        private val apiService: ApiService,
        private val rxSchedulers: RxSchedulers,
        private val parkingNavigatorFactory: ParkingNavigatorFactory) : BasePresenter(), ParkingListAdapter.Listener {

    override fun onAttach() {
        fetchParkingStatus()
    }

    override fun onItemClick(item: Parking) = parkingNavigatorFactory.create(item).navigate()

    private fun fetchParkingStatus() {
        apiService.getParkingList()
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.ui)
                .doOnSuccess { view.setParkingList(it) }
                .subscribeEmpty()
    }
}
