package com.parkingapp.ui.parking.detail

interface IParking {

    interface View {
        fun showParkingName(text: String)
        fun showContactDetails(info: String)
        fun showAddress(address: String)
        fun showMap()
        fun setMapUrl(url: String)
    }
}
