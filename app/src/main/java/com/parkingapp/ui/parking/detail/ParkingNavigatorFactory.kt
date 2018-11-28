package com.parkingapp.ui.parking.detail

import android.app.Activity
import com.parkingapp.ui.navigator.Navigator
import com.parkingapp.ui.parking.domain.Parking
import com.parkingapp.ui.parking.domain.ParkingDto
import javax.inject.Inject

class ParkingNavigatorFactory @Inject constructor(private val activity: Activity) {

    fun create(item: Parking): Navigator = ParkingNavigator(activity, item)
}
