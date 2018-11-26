package com.sniped.ui.main

import com.nhaarman.mockitokotlin2.verify
import com.sniped.test.MockitoTest
import com.sniped.ui.parking.IParkingList
import com.sniped.ui.parking.ParkingListPresenter
import com.sniped.ui.parking.detail.ParkingNavigator
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ParkingListPresenterTest : MockitoTest() {

    @Mock private lateinit var view: IParkingList.View
    @Mock private lateinit var parkingNavigator: ParkingNavigator

    private lateinit var presenter: ParkingListPresenter

    @Before
    fun setUp() {
        presenter = ParkingListPresenter(
                parkingNavigator)
    }

    @Test
    fun whenClickingDetailButton_shouldNavigateToDetailView() {
        presenter.onDetailButtonClick()

        verify(parkingNavigator).navigate()
    }
}
