package com.sniped.ui.parking

import com.sniped.ui.parking.domain.Parking

interface IParkingList {

    interface View {
        fun setParkingList(list: List<Parking>)
    }
}
