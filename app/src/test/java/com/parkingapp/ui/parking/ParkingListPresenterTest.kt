package com.parkingapp.ui.parking

import android.graphics.Color
import com.google.android.gms.maps.model.LatLng
import com.nhaarman.mockitokotlin2.*
import com.parkingapp.core.RxSchedulers
import com.parkingapp.test.MockitoTest
import com.parkingapp.ui.navigator.Navigator
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.*
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock

class ParkingListPresenterTest : MockitoTest() {

    @Mock private lateinit var view: IParkingList.View
    @Mock private lateinit var getParkingList: GetParkingList
    @Mock private lateinit var parkingListTransformer: ParkingListTransformer
    @Mock private lateinit var counterToColorMapper: CounterToColorMapper
    @Mock private lateinit var rxSchedulers: RxSchedulers
    @Mock private lateinit var locationGetter: LocationGetter
    @Mock private lateinit var parkingNavigatorFactory: ParkingNavigatorFactory

    private lateinit var presenter: ParkingListPresenter

    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        whenever(rxSchedulers.io).thenReturn(testScheduler)
        whenever(rxSchedulers.ui).thenReturn(testScheduler)

        whenever(parkingListTransformer.forLocation(isA(), isA())).thenReturn(listOf(mock()))
        whenever(counterToColorMapper.map(anyInt())).thenReturn(Color.RED)
        whenever(locationGetter.get()).thenReturn(Observable.just(LocationGetter.NO_LOCATION))
        whenever(getParkingList.poll()).thenReturn(Observable.just(emptyList()))

        presenter = ParkingListPresenter(
                view,
                getParkingList,
                locationGetter,
                parkingListTransformer,
                counterToColorMapper,
                rxSchedulers,
                parkingNavigatorFactory)
    }

    @Test
    fun onAttach_shouldPollParkingLotsEndpoint() {
        presenter.onAttach()

        verify(getParkingList).poll()
        assertThat(presenter.disposeBag.size()).isGreaterThan(0)
    }

    @Test
    fun onAttach_shouldListenToLocationUpdates() {
        presenter.onAttach()

        verify(locationGetter).get()
        assertThat(presenter.disposeBag.size()).isGreaterThan(0)
    }

    @Test
    fun onAttach_shouldListenToStateChanges() {
        presenter.onAttach()
        testScheduler.triggerActions()

        assertThat(presenter.state.hasValue()).isTrue()
    }

    @Test
    fun onAttach_shouldUpdateStateWithFetchedData() {
        presenter.onAttach()
        testScheduler.triggerActions()

        presenter.state.test().assertValue(ParkingListPresenter.State(LocationGetter.NO_LOCATION, emptyList(), 0))
    }

    @Test
    fun onStateChange_withDefaultValues_shouldHideProgressView() {
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(LocationGetter.NO_LOCATION, emptyList(), 0))

        verify(view).hideProgressView()
    }

    @Test
    fun onStateChange_withItemsAndLocationInfo_shouldAddTransformedListToView() {
        val transformedList = listOf<Parking>(mock())
        whenever(parkingListTransformer.forLocation(isA(), isA())).thenReturn(transformedList)
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(LatLng(1.0, 2.0), transformedList, 0))

        verify(view).setParkingList(transformedList)
    }

    @Test
    fun onStateChange_shouldSetCounterValue() {
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(mock(), mock(), 15))

        verify(view).setCounterValue(15)
    }

    @Test
    fun onStateChange_shouldSetCounterColor() {
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(mock(), mock(), 0))

        verify(view).setCounterColor(Color.RED)
    }

    @Test
    fun onStateChange_withItemsAndNoLocationInfo_shouldAddListToView() {
        val parkingList = listOf(Parking.NULL, mock(), mock())
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(LocationGetter.NO_LOCATION, parkingList, 0))

        verify(view).setParkingList(parkingList)
    }

    @Test
    fun onStateChange_withItems_shouldHideErrorView() {
        presenter.onAttach()

        presenter.state.onNext(ParkingListPresenter.State(mock(), listOf(mock()), 0))

        verify(view).hideErrorView()
    }

    @Test
    fun onStateChange_withError_shouldShowErrorView() {
        presenter.onAttach()

        presenter.state.onError(mock())

        verify(view).showErrorView()
    }

    @Test
    fun onStateChange_withError_whenViewHasItems_shouldNotErrorView() {
        whenever(view.hasItems()).thenReturn(true)
        presenter.onAttach()

        presenter.state.onError(mock())

        verify(view, never()).showErrorView()
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

    @Test
    fun onRefresh_shouldResetLocationGetter() {
        presenter.onRefresh()

        verify(locationGetter).reset()
    }

    @Test
    fun onRefresh_shouldResetState() {
        presenter.onAttach()
        testScheduler.triggerActions()
        assertThat(presenter.state.hasValue()).isTrue()

        presenter.onRefresh()

        assertThat(presenter.state.hasValue()).isFalse()
    }
}
