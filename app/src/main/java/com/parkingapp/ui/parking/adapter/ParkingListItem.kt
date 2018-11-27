package com.parkingapp.ui.parking.adapter

import com.parkingapp.R
import com.parkingapp.ui.adapter.BaseAdapterItem
import com.parkingapp.ui.parking.domain.Parking
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.view_parking_list_item.view.*

class ParkingListItem(
        val data: Parking) : BaseAdapterItem() {

    override fun getLayout() = R.layout.view_parking_list_item

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            icon_text_view.text = data.name.substring(0..2)
            title_view.text = data.description
            subtitle_view.text = data.availableCapacity.toString()

            if (!data.distance.isNaN()) {
                distance_view.text = data.distance.toString()
            }
        }
    }
}