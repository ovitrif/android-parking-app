package com.parkingapp.api

import com.parkingapp.ui.parking.domain.Parking
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("4/mobiliteit/bezettingparkingsrealtime.json")
    fun getParkingList(): Single<List<Parking>>
}
