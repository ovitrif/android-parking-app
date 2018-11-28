package com.parkingapp.ui.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.parkingapp.core.RxSchedulers
import com.parkingapp.test.MockitoTest
import com.parkingapp.ui.navigator.Navigator
import com.parkingapp.ui.parking.IParkingList
import com.parkingapp.ui.parking.ParkingListPresenter
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.GetParkingList
import com.parkingapp.ui.parking.domain.LocationGetter
import com.parkingapp.ui.parking.domain.Parking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ParkingListPresenterTest : MockitoTest() {

    @Mock private lateinit var view: IParkingList.View
    @Mock private lateinit var getParkingList: GetParkingList
    @Mock private lateinit var rxSchedulers: RxSchedulers
    @Mock private lateinit var locationGetter: LocationGetter
    @Mock private lateinit var parkingNavigatorFactory: ParkingNavigatorFactory

    private lateinit var presenter: ParkingListPresenter

    @Before
    fun setUp() {
        presenter = ParkingListPresenter(
                view,
                getParkingList,
                locationGetter,
                rxSchedulers,
                parkingNavigatorFactory)
    }

    @Test
    fun onItemClick_shouldNavigateToDetailView() {
        val parkingNavigator = mock<Navigator>()
        val item = Parking.NULL
        whenever(parkingNavigatorFactory.create(item)).thenReturn(parkingNavigator)

        presenter.onItemClick(item)

        verify(parkingNavigatorFactory).create(item)
        verify(parkingNavigator).navigate()
    }
}
