package com.parkingapp.ui.parking

import androidx.annotation.VisibleForTesting
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.model.LatLng
import com.parkingapp.core.RxSchedulers
import com.parkingapp.extensions.subscribeEmpty
import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.parking.adapter.ParkingListAdapter
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.*
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val view: IParkingList.View,
        private val getParkingList: GetParkingList,
        private val locationGetter: LocationGetter,
        private val parkingListTransformer: ParkingListTransformer,
        private val counterToColorMapper: CounterToColorMapper,
        private val rxSchedulers: RxSchedulers,
        private val parkingNavigatorFactory: ParkingNavigatorFactory)
    : BasePresenter(), ParkingListAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    @VisibleForTesting
    internal var state: BehaviorSubject<State> = BehaviorSubject.create()

    override fun onAttach() {
        disposeBag += fetchParkingLots().subscribeEmpty()
        disposeBag += fetchLocation().subscribeEmpty()
        disposeBag += listenToStateChanges().subscribeEmpty()
    }

    override fun onItemClick(item: Parking) = parkingNavigatorFactory.create(item).navigate()

    override fun onRefresh() {
        locationGetter.reset()
        onDetach()
        state = BehaviorSubject.create()
        onAttach()
    }

    private fun fetchParkingLots(): Observable<List<Parking>> {
        return getParkingList.poll()
                .observeOn(rxSchedulers.ui)
                .doOnNext { items -> state.onNext(State(state.latLng, items, items.sumBy { it.availableCapacity })) }
                .doOnError(state::onError)
    }

    private fun fetchLocation(): Observable<LatLng> {
        return locationGetter.get()
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.ui)
                .doOnNext { state.onNext(State(it, state.parkingLots, state.counter)) }
    }

    private fun listenToStateChanges(): Observable<State> {
        return state
                .doOnEach {
                    view.hideProgressView()
                }
                .doOnError {
                    if (!view.hasItems()) {
                        view.showErrorView()
                    }
                }
                .doOnNext(this::onUpdate)
    }

    private fun onUpdate(data: State) {
        if (data.parkingLots.isNotEmpty()) {
            when {
                data.latLng.isNotEmpty() -> {
                    val decoratedList = parkingListTransformer.forLocation(data.parkingLots, data.latLng)
                    view.setParkingList(decoratedList)
                }
                else -> view.setParkingList(data.parkingLots)
            }
            view.hideErrorView()

            view.setCounterValue(data.counter)
            view.setCounterColor(counterToColorMapper.map(data.counter))
        }
    }

    private fun LatLng.isNotEmpty() = this != LocationGetter.NO_LOCATION

    private val BehaviorSubject<State>.latLng get() = this.value?.latLng ?: LocationGetter.NO_LOCATION
    private val BehaviorSubject<State>.parkingLots get() = this.value?.parkingLots ?: emptyList()
    private val BehaviorSubject<State>.counter get() = this.value?.counter ?: 0

    data class State(
            val latLng: LatLng,
            val parkingLots: List<Parking>,
            val counter: Int)
}
