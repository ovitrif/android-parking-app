package com.parkingapp.api

import com.parkingapp.ui.parking.domain.ParkingDto
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("4/mobiliteit/bezettingparkingsrealtime.json")
    fun getParkingList(): Observable<List<ParkingDto>>
}
