package com.parkingapp.ui.parking.domain

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.model.LatLng
import com.patloew.rxlocation.RxLocation
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import javax.inject.Inject

class LocationGetter @Inject constructor(
        private val rxLocation: RxLocation,
        private val rxPermissions: RxPermissions) {

    private var arePermissionsGranted: Observable<Boolean>? = null
    private var isLocationEnabled: Observable<Boolean>? = null

    private var cachedLocation = NO_LOCATION

    private var locationRequest = LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5 * 1000)
            .setFastestInterval(5 * 1000)
            .setSmallestDisplacement(25F)

    fun reset() {
        arePermissionsGranted = null
        isLocationEnabled = null
        cachedLocation = NO_LOCATION
    }

    @SuppressLint("MissingPermission")
    fun get(): Observable<LatLng> {
        return requestPermissions()
                .flatMap { arePermissionsGranted ->
                    when {
                        arePermissionsGranted -> requestLocationSettings()
                        else -> this.arePermissionsGranted
                    }
                }
                .flatMap { isLocationEnabled ->
                    when {
                        isLocationEnabled -> rxLocation.location().updates(locationRequest)
                                .map { LatLng(it.latitude, it.longitude) }
                                .onErrorResumeNext(getLastKnownLocation())
                                .doOnNext { cachedLocation = it }
                        else -> Observable.just(cachedLocation)
                    }
                }
    }

    @SuppressLint("MissingPermission")
    private fun getLastKnownLocation(): Observable<LatLng> {
        return rxLocation.location().lastLocation()
                .map { LatLng(it.latitude, it.longitude) }
                .toSingle(cachedLocation)
                .toObservable()
    }

    private fun requestPermissions(): Observable<Boolean> {
        return arePermissionsGranted ?: rxPermissions
                .request(ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
                .onErrorReturn { false }
                .doOnNext { arePermissionsGranted = Observable.just(it) }
    }

    private fun requestLocationSettings(): Observable<Boolean> {
        return isLocationEnabled ?: rxLocation.settings()
                .checkAndHandleResolution(locationRequest)
                .doOnSuccess { isLocationEnabled = Observable.just(it) }
                .toObservable()

    }

    companion object {
        val NO_LOCATION = LatLng(Double.NaN, Double.NaN)
    }
}
