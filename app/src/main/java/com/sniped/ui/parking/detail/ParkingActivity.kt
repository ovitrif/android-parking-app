package com.sniped.ui.parking.detail

import android.os.Bundle
import butterknife.ButterKnife
import com.sniped.App
import com.sniped.R
import com.sniped.ui.BaseActivity
import com.sniped.ui.navigator.NavigatorModule
import com.sniped.ui.parking.detail.di.DaggerParkingComponent
import com.sniped.ui.parking.detail.di.ParkingModule
import com.sniped.ui.parking.domain.Parking
import kotlinx.android.synthetic.main.view_app_bar.*

class ParkingActivity : BaseActivity(), IParking.View {

    override lateinit var presenter: ParkingPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking)
        ButterKnife.bind(this)

        val parkingViewModel = intent.getParcelableExtra(ParkingNavigator.EXTRA_PARKING_ITEM) ?: Parking.NULL
        val component = DaggerParkingComponent.builder()
                .appComponent(App.component)
                .navigatorModule(NavigatorModule(this))
                .parkingModule(ParkingModule(this, parkingViewModel))
                .build()
        presenter = component.presenter()

        initView()
        presenter.onAttach()
    }

    override fun setTitle(text: String){
        supportActionBar?.title = text
    }

    override fun onBackPressed() = presenter.onBackPressed()

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}
