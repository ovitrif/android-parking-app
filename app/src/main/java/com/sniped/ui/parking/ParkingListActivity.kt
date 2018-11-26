package com.sniped.ui.parking

import android.os.Bundle
import butterknife.ButterKnife
import com.sniped.App
import com.sniped.R
import com.sniped.ui.BaseActivity
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.adapter.ParkingListAdapter
import com.sniped.ui.parking.di.DaggerParkingListComponent
import com.sniped.ui.parking.di.ParkingListModule
import com.sniped.ui.parking.domain.Parking
import kotlinx.android.synthetic.main.activity_parking_list.*
import kotlinx.android.synthetic.main.view_app_bar.*

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
                .parkingListModule(ParkingListModule(this))
                .build()
        presenter = component.presenter()

        initView()
        presenter.onAttach()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    override fun setParkingList(list: List<Parking>) = adapter.setData(list)

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.parking_list_title)
        parking_list.adapter = adapter
    }
}
