package com.sniped.ui.parking.adapter

import com.sniped.ui.adapter.BaseAdapter
import com.sniped.ui.parking.domain.Parking

class ParkingListAdapter : BaseAdapter() {

    fun setData(list: List<Parking>) {
        clear()
        addAll(list.map(::ParkingListItem))
    }
}