package com.parkingapp.ui.parking.domain

import com.parkingapp.api.ApiService
import com.parkingapp.core.RxSchedulers
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GetParkingList @Inject constructor(
        private val apiService: ApiService,
        private val rxSchedulers: RxSchedulers) {

    fun poll(): Observable<List<Parking>> {
        return Observable.interval(0, 1, TimeUnit.MINUTES, rxSchedulers.computation)
                .subscribeOn(rxSchedulers.io)
                .flatMap { execute() }
                .observeOn(rxSchedulers.ui)
    }

    private fun execute(): Observable<List<Parking>> {
        return apiService.getParkingList()
                .subscribeOn(rxSchedulers.io)
                .map { it.map(Parking.Companion::fromDto) }
                .map { it.sortedBy(Parking::description) }
    }
}