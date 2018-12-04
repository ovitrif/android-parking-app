package com.parkingapp.ui.parking.detail

import android.os.Bundle
import butterknife.ButterKnife
import com.parkingapp.App
import com.parkingapp.R
import com.parkingapp.ui.BaseActivity
import com.parkingapp.ui.navigator.NavigatorModule
import com.parkingapp.ui.parking.detail.di.DaggerParkingComponent
import com.parkingapp.ui.parking.detail.di.ParkingModule
import com.parkingapp.ui.parking.domain.Parking
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_parking.*

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

    override fun onBackPressed() = presenter.onBackPressed()

    override fun showParkingName(text: String) = supportActionBar?.let { title = text } ?: Unit

    override fun showContactDetails(info: String) = with(contact_details_view) { text = info }

    override fun showAddress(address: String) = with(address_view) { text = address }

    override fun showMap() = with(map_view) { visibility = android.view.View.VISIBLE }

    override fun setMapUrl(url: String) {
        if (url.isNotBlank()) {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerCrop()
                    .into(map_view)
        }
    }

    private fun initView() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }
}
