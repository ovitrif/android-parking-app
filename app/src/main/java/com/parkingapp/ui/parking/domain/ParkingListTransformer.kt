package com.parkingapp.ui.parking.domain

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import javax.inject.Inject

class ParkingListTransformer @Inject constructor() {

    fun forLocation(items: List<Parking>, latLng: LatLng): List<Parking> {
        return items
                .map { it.copy(distance = SphericalUtil.computeDistanceBetween(latLng, it.latLng)) }
                .sortedBy { it.distance }
    }
}
