package com.parkingapp.ui.parking.detail

import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.parkingapp.test.MockitoTest
import com.parkingapp.ui.navigator.BackNavigator
import com.parkingapp.ui.parking.detail.domain.StaticMapUrlGenerator
import com.parkingapp.ui.parking.domain.Parking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.never

class ParkingPresenterTest : MockitoTest() {

    @Mock private lateinit var view: IParking.View
    @Mock private lateinit var backNavigator: BackNavigator
    @Mock private lateinit var staticMapUrlGenerator: StaticMapUrlGenerator

    private val parking = Parking.NULL

    private lateinit var presenter: ParkingPresenter

    @Before
    fun setUp() {
        whenever(staticMapUrlGenerator.fromLatLng(parking.latLng)).thenReturn("staticMapUrl")

        presenter = ParkingPresenter(
                view,
                parking,
                staticMapUrlGenerator,
                backNavigator)
    }

    @Test
    fun onAttach_shouldShowParkingName() {
        presenter.onAttach()

        verify(view).showParkingName(parking.name)
    }

    @Test
    fun onAttach_shouldShowContactDetails() {
        presenter.onAttach()

        verify(view).showContactDetails(parking.contactInfo)
    }

    @Test
    fun onAttach_shouldShowAddress() {
        presenter.onAttach()

        verify(view).showAddress(parking.address)
    }

    @Test
    fun onAttach_withLocation_shouldShowLocationMap() {
        presenter.onAttach()

        verify(view).showMap()
        verify(view).setMapUrl(staticMapUrlGenerator.fromLatLng(parking.latLng))
    }

    @Test
    fun onAttach_withoutLocation_shouldHideLocationMap() {
        whenever(staticMapUrlGenerator.fromLatLng(parking.latLng)).thenReturn("")

        presenter.onAttach()

        verify(view, never()).showMap()
        verify(view, never()).setMapUrl(isA())
    }

    @Test
    fun onBackPressed_shouldNavigateBack() {
        presenter.onBackPressed()

        verify(backNavigator).navigate()
    }
}