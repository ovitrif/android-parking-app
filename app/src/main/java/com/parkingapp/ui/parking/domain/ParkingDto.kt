package com.parkingapp.ui.parking.domain

import com.google.gson.annotations.SerializedName

data class ParkingDto(
        val id: Int = 0,
        val address: String = "",
        val capacityRounding: Int = 0,
        val city: City = City(),
        val contactInfo: String = "",
        val description: String = "",
        val lastModifiedDate: String = "",
        val latitude: Double = 0.0,
        val longitude: Double = 0.0,
        val name: String = "",
        val openingTimes: List<OpeningTime> = listOf(),
        val parkingServer: ParkingServer = ParkingServer(),
        val parkingStatus: ParkingStatus = ParkingStatus(),
        val suggestedFreeThreshold: Int = 0,
        val suggestedFullThreshold: Int = 0,
        val totalCapacity: Int = 0) {

    data class City(
            val id: Int = 0,
            val name: String = "")

    data class ParkingServer(
            val id: Int = 0,
            val name: String = "")

    data class OpeningTime(
            val days: List<String> = listOf(),
            val from: String = "",
            val to: String = "")

    data class ParkingStatus(
            val activeRoute: String = "",
            val availableCapacity: Int = 0,
            val lastModifiedDate: String = "",
            @SerializedName("open") val isOpen: Boolean = false,
            val suggestedCapacity: String = "",
            val totalCapacity: Int = 0)

    companion object {
        val NULL = ParkingDto()
    }
}