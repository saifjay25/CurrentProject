package com.mycode.currentapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.mycode.currentapp.R
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private var latitude : Double = 0.0
    private var longitude : Double = 0.0
    private var name : String? = null
    private var address : String? = null
    private var price : String? = null
    private var rating : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        val extras : Bundle = intent.extras
        latitude = extras.getDouble("latitude")
        longitude = extras.getDouble("longitude")
        name = extras.getString("name")
        setTitle(name)
        address = extras.getString("address")
        price = extras.getString("price")
        rating = extras.getString("rating")
        pricelevelRating.text = "$price · $rating"
        mapsAddress.text = address
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        val latlng = LatLng(latitude, longitude)
        googleMap.addMarker(MarkerOptions().position(latlng))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,15f))
    }

}
