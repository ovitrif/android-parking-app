package com.parkingapp.ui.parking

import com.parkingapp.ui.parking.domain.Parking

interface IParkingList {

    interface View {
        fun setParkingList(list: List<Parking>)
    }
}
