package com.sniped.ui.main

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sniped.api.ApiService
import com.sniped.core.RxSchedulers
import com.sniped.test.MockitoTest
import com.sniped.ui.navigator.Navigator
import com.sniped.ui.parking.IParkingList
import com.sniped.ui.parking.ParkingListPresenter
import com.sniped.ui.parking.detail.ParkingNavigatorFactory
import com.sniped.ui.parking.domain.Parking
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class ParkingListPresenterTest : MockitoTest() {

    private val testScheduler = TestScheduler()

    @Mock private lateinit var view: IParkingList.View
    @Mock private lateinit var apiService: ApiService
    @Mock private lateinit var rxSchedulers: RxSchedulers
    @Mock private lateinit var parkingNavigatorFactory: ParkingNavigatorFactory

    private lateinit var presenter: ParkingListPresenter

    @Before
    fun setUp() {
        whenever(rxSchedulers.io).thenReturn(testScheduler)
        whenever(rxSchedulers.ui).thenReturn(testScheduler)
        presenter = ParkingListPresenter(
                view,
                apiService,
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
