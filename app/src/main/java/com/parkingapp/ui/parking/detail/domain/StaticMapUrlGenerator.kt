package com.parkingapp.ui.parking.detail.domain

import com.google.android.gms.maps.model.LatLng
import com.mypopsy.maps.StaticMap
import com.parkingapp.BuildConfig
import com.parkingapp.ui.parking.domain.LocationGetter
import javax.inject.Inject

class StaticMapUrlGenerator @Inject constructor() {

    fun fromLatLng(latLng: LatLng): String {
        return when {
            latLng != LocationGetter.NO_LOCATION -> StaticMap()
                    .center(latLng.latitude, latLng.longitude)
                    .key(BuildConfig.GOOGLE_STATIC_MAPS_KEY)
                    .size(640, 480)
                    .zoom(17)
                    .marker(StaticMap.Marker.Style.RED, StaticMap.GeoPoint(latLng.latitude, latLng.longitude))
                    .toString()
            else -> ""
        }
    }
}
