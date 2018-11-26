package com.sniped.ui.parking.detail

import android.app.Activity
import android.content.Intent
import com.sniped.ui.navigator.Navigator
import com.sniped.ui.parking.domain.Parking
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
