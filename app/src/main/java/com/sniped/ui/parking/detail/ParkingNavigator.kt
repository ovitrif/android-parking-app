package com.sniped.ui.parking.detail

import android.app.Activity
import android.content.Intent
import com.sniped.ui.navigator.Navigator
import javax.inject.Inject

class ParkingNavigator @Inject constructor(private val activity: Activity) : Navigator {

    override fun navigate() {
        val intent = Intent(activity, ParkingActivity::class.java)

        activity.startActivity(intent)
    }
}
