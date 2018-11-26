package com.sniped.ui.parking.adapter

import com.sniped.R
import com.sniped.ui.adapter.BaseAdapterItem
import com.sniped.ui.parking.domain.Parking
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.view_parking_list_item.view.*

class ParkingListItem(
        val data: Parking) : BaseAdapterItem() {

    override fun getLayout() = R.layout.view_parking_list_item

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            icon_text_view.text = data.name.split(" ").first()
            title_view.text = data.description
            subtitle_view.text = data.parkingStatus.availableCapacity.toString()
            // TODO calculate and use distance
            distance_view.text = data.latitude.toString()
        }
    }
}