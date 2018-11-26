package com.sniped.ui.parking.detail

import android.os.Bundle
import butterknife.ButterKnife
import com.sniped.App
import com.sniped.R
import com.sniped.ui.BaseActivity
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.detail.di.DaggerParkingComponent
import com.sniped.ui.parking.detail.di.ParkingModule
import kotlinx.android.synthetic.main.view_app_bar.*

class ParkingActivity : BaseActivity(), IParking.View {

    override lateinit var presenter: ParkingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        ButterKnife.bind(this)

        val component = DaggerParkingComponent.builder()
                .appComponent(App.component)
                .parkingModule(ParkingModule(this))
                .navigatorModule(NavigatorModule(this))
                .build()
        presenter = component.presenter()

        initView()
        presenter.onAttach()
    }

    override fun onBackPressed() = presenter.onBackPressed()

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}
