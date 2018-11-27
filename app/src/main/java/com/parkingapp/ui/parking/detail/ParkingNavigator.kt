package com.parkingapp.ui.parking.detail

import android.app.Activity
import android.content.Intent
import com.parkingapp.ui.navigator.Navigator
import com.parkingapp.ui.parking.domain.Parking
import com.parkingapp.ui.parking.domain.ParkingDto
import javax.inject.Inject

class ParkingNavigator @Inject constructor(
        private val activity: Activity,
        private val item: Parking) : Navigator {

    override fun navigate() {
        val intent = Intent(activity, ParkingActivity::class.java)
        intent.putExtra(EXTRA_PARKING_ITEM, item)

        activity.startActivity(intent)
    }

    companion object {
        const val EXTRA_PARKING_ITEM = "EXTRA_PARKING_ITEM"
    }
}
