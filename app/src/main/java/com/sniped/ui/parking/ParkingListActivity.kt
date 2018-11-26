package com.sniped.ui.parking

import android.os.Bundle
import butterknife.ButterKnife
import com.sniped.App
import com.sniped.R
import com.sniped.ui.BaseActivity
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.di.DaggerParkingListComponent
import kotlinx.android.synthetic.main.view_app_bar.*

class ParkingListActivity : BaseActivity(), IParkingList.View {

    override lateinit var presenter: ParkingListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_list)
        ButterKnife.bind(this)

        val component = DaggerParkingListComponent.builder()
                .appComponent(App.component)
                .navigatorModule(NavigatorModule(this))
                .build()
        presenter = component.presenter()

        initView()
        presenter.onAttach()
    }

    override fun onBackPressed() {
        moveTaskToBack(true)
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.parking_list_title)
    }
}
