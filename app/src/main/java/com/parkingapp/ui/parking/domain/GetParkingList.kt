package com.parkingapp.ui.parking.domain

import com.parkingapp.api.ApiService
import com.parkingapp.core.RxSchedulers
import io.reactivex.Single
import javax.inject.Inject

class GetParkingList @Inject constructor(
        private val apiService: ApiService,
        private val rxSchedulers: RxSchedulers) {

    fun execute(): Single<List<Parking>> {
        return apiService.getParkingList()
                .subscribeOn(rxSchedulers.io)
                .map { it.map(Parking.Companion::fromDto) }
    }
}