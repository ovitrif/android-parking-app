package com.parkingapp.ui.parking.domain

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Parking(
        val name: String = "",
        val address: String = "",
        val contactInfo: String = "",
        val description: String = "",
        val latLng: LatLng = LatLng(Double.NaN, Double.NaN),
        val totalCapacity: Int = 0,
        val availableCapacity: Int = 0,
        val distance: Double = Double.NaN) : Parcelable {

    companion object {
        val NULL = Parking()

        fun fromDto(dto: ParkingDto): Parking {
            return Parking(
                    name = dto.name,
                    address = dto.address,
                    contactInfo = dto.contactInfo,
                    description = dto.description,
                    latLng = LatLng(dto.latitude, dto.longitude),
                    totalCapacity = dto.totalCapacity,
                    availableCapacity = dto.parkingStatus.availableCapacity)
        }
    }
}