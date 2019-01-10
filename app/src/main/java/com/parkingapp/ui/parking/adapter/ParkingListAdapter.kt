package com.parkingapp.ui.parking.adapter

import com.parkingapp.ui.adapter.BaseAdapter
import com.parkingapp.ui.parking.domain.Parking

class ParkingListAdapter : BaseAdapter() {

    fun setData(list: List<ParkingListItem>) {
        clear()
        addAll(list)
    }

    fun setListener(listener: Listener) {
        this.setOnItemClickListener { item, _ ->
            if (item is ParkingListItem) {
                listener.onItemClick(item.data)
            }
        }
    }

    interface Listener {
        fun onItemClick(item: Parking)
    }
}