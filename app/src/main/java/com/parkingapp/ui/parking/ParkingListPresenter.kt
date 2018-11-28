package com.parkingapp.ui.parking

import android.graphics.Color
import androidx.core.graphics.ColorUtils
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil
import com.parkingapp.core.RxSchedulers
import com.parkingapp.extensions.subscribeEmpty
import com.parkingapp.ui.BasePresenter
import com.parkingapp.ui.parking.adapter.ParkingListAdapter
import com.parkingapp.ui.parking.detail.ParkingNavigatorFactory
import com.parkingapp.ui.parking.domain.GetParkingList
import com.parkingapp.ui.parking.domain.LocationGetter
import com.parkingapp.ui.parking.domain.Parking
import io.reactivex.Observable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ParkingListPresenter @Inject constructor(
        private val view: IParkingList.View,
        private val getParkingList: GetParkingList,
        private val locationGetter: LocationGetter,
        private val rxSchedulers: RxSchedulers,
        private val parkingNavigatorFactory: ParkingNavigatorFactory) : BasePresenter(), ParkingListAdapter.Listener, SwipeRefreshLayout.OnRefreshListener {

    private var state: BehaviorSubject<State> = BehaviorSubject.create()

    override fun onAttach() {
        disposeBag += fetchParkingLots().subscribeEmpty()
        disposeBag += fetchLocation().subscribeEmpty()

        disposeBag += state
                .doOnEach {
                    view.hideProgressView()

                    if (it.isOnError && !view.hasItems()) {
                        view.showErrorView()
                    }
                }
                .doOnNext(this::onUpdate)
                .subscribeEmpty()
    }

    override fun onItemClick(item: Parking) = parkingNavigatorFactory.create(item).navigate()

    override fun onRefresh() {
        locationGetter.reset()
        onDetach()
        state = BehaviorSubject.create()
        onAttach()
    }

    private fun onUpdate(data: State) {
        if (data.parkingLots.isNotEmpty()) {
            when {
                data.latLng.isNotEmpty() -> view.setParkingList(data.sortItemsByDistance())
                else -> view.setParkingList(data.parkingLots)
            }
            view.hideErrorView()

            view.setCounterValue(data.counter)
            view.setCounterColor(data.counter.toCounterColor())
        }
    }

    private fun fetchParkingLots(): Observable<List<Parking>> {
        return Observable.interval(0, 1, TimeUnit.MINUTES)
                .subscribeOn(rxSchedulers.io)
                .flatMap { getParkingList.execute() }
                .observeOn(rxSchedulers.ui)
                .doOnNext { items ->
                    view.hideProgressView()

                    val latLng = state.value?.latLng ?: LocationGetter.NO_LOCATION
                    val counter = items.sumBy { it.availableCapacity }
                    state.onNext(State(latLng, items, counter))
                }
                .doOnError(state::onError)
    }

    private fun fetchLocation(): Observable<LatLng> {
        return locationGetter.get()
                .subscribeOn(rxSchedulers.io)
                .observeOn(rxSchedulers.ui)
                .doOnNext { latLng ->
                    view.hideProgressView()

                    val counter = state.value?.counter ?: 0
                    val items = state.value?.parkingLots ?: emptyList()
                    state.onNext(State(latLng, items, counter))
                }
    }

    private fun LatLng.isNotEmpty() = this != LocationGetter.NO_LOCATION

    private fun State.sortItemsByDistance(): List<Parking> {
        return this.parkingLots
                .map { it.copy(distance = SphericalUtil.computeDistanceBetween(latLng, it.latLng)) }
                .sortedBy { it.distance }
    }

    private fun Int.toCounterColor(): Int {
        val colorRatio = this.coerceAtMost(255) / 255F
        return ColorUtils.blendARGB(Color.RED, Color.GREEN, colorRatio)
    }

    private data class State(
            val latLng: LatLng,
            val parkingLots: List<Parking>,
            val counter: Int)
}
