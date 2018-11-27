package com.parkingapp.ui.parking.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Parking(
        val name: String = "",
        val address: String = "",
        val contactInfo: String = "",
        val description: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val distance: Double = Double.NaN,
        val totalCapacity: Int = 0,
        val availableCapacity: Int = 0) : Parcelable {

    companion object {
        val NULL = Parking()

        fun fromDto(dto: ParkingDto): Parking {
            return Parking(
                    name = dto.name,
                    address = dto.address,
                    contactInfo = dto.contactInfo,
                    description = dto.description,
                    latitude = dto.latitude,
                    longitude = dto.longitude,
                    totalCapacity = dto.totalCapacity,
                    availableCapacity = dto.parkingStatus.availableCapacity)
        }
    }
}