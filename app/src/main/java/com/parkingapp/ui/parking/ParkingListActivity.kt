package com.parkingapp.ui.parking

import android.os.Bundle
import android.view.View
import butterknife.ButterKnife
import com.parkingapp.App
import com.parkingapp.R
import com.parkingapp.ui.BaseActivity
import com.parkingapp.ui.navigator.NavigatorModule
import com.parkingapp.ui.parking.adapter.ParkingListAdapter
import com.parkingapp.ui.parking.adapter.ParkingListItem
import com.parkingapp.ui.parking.di.DaggerParkingListComponent
import com.parkingapp.ui.parking.di.ParkingListModule
import com.parkingapp.ui.parking.domain.Parking
import kotlinx.android.synthetic.main.activity_parking_list.*
import kotlinx.android.synthetic.main.app_bar_parking_list.*

class ParkingListActivity : BaseActivity(), IParkingList.View {

    private val adapter = ParkingListAdapter()
    override lateinit var presenter: ParkingListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_list)
        ButterKnife.bind(this)

        val component = DaggerParkingListComponent.builder()
                .appComponent(App.component)
                .navigatorModule(NavigatorModule(this))
                .parkingListModule(ParkingListModule(this, this))
                .build()
        presenter = component.presenter()

        initView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttach()
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetach()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun setParkingList(list: List<Parking>) {
        parking_list.visibility = View.VISIBLE
        adapter.setData(list.map(::ParkingListItem))
    }

    override fun setCounterValue(count: Int) = with(count_view) { text = count.toString() }

    override fun setCounterColor(@ColorInt color: Int) = with(count_view) {
        setTextColor(color)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            compoundDrawableTintList = ColorStateList.valueOf(color)
        }
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setCustomView(R.layout.app_bar_parking_list)
        toolbar_title.setText(R.string.app_name)

        refresh_view.setOnRefreshListener(presenter)

        parking_list.adapter = adapter
        adapter.setListener(presenter)
    }
}
