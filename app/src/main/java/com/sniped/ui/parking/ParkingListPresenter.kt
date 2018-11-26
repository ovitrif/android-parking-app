package com.sniped.ui.parking

import com.sniped.api.ApiService
import com.sniped.core.RxSchedulers
import com.sniped.extensions.subscribeEmpty
import com.sniped.ui.BasePresenter
import com.sniped.ui.parking.adapter.ParkingListAdapter
import com.sniped.ui.parking.detail.ParkingNavigatorFactory
import com.sniped.ui.parking.domain.Parking
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
