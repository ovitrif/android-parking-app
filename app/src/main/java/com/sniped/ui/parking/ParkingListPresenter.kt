package com.sniped.ui.parking

import com.sniped.api.ApiService
import com.sniped.core.RxSchedulers
import com.sniped.extensions.subscribeEmpty
import com.sniped.ui.BasePresenter
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val view: IParkingList.View,
        private val apiService: ApiService,
        private val rxSchedulers: RxSchedulers) : BasePresenter() {

    override fun onAttach() {
        fetchParkingStatus()
    }

    private fun fetchParkingStatus() {
        apiService.getParkingList()
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.ui)
                .doOnSuccess { view.setParkingList(it) }
                .subscribeEmpty()
    }
}
