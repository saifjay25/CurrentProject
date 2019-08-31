package com.mycode.currentapp.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.mycode.currentapp.adapter.RecyclerViewAdapter
import com.mycode.currentapp.entities.PlacesResults
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import com.mycode.currentapp.viewmodel.ViewModelProviderFactory
import javax.inject.Inject
import android.content.Context
import android.content.Intent
import androidx.annotation.NonNull
import com.mycode.currentapp.R
import com.mycode.currentapp.entities.Result


class MainActivity : DaggerAppCompatActivity(), RecyclerViewAdapter.OnitemClickListener{

    companion object{
        var locationCounter = 0
    }
    private lateinit var adapter : RecyclerViewAdapter
    private lateinit var location : LocationManager
    private var latitude = 0.0
    private var longitude = 0.0
    private lateinit var viewModel : MainViewModel
    private lateinit var currentLocation : String
    private lateinit var results : MutableList<Result>
    var providerFactory : ViewModelProviderFactory? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this, providerFactory).get(MainViewModel::class.java)
        getLocationPermission()
    }

    override fun itemOnClick(position: Int) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("name", results[position].name)
        intent.putExtra("rating", results[position].rating)
        intent.putExtra("price", results[position].priceSign)
        intent.putExtra("address", results[position].address)
        intent.putExtra("latitude", results[position].geometry?.location?.lat)
        intent.putExtra("longitude", results[position].geometry?.location?.lon)
        startActivity(intent)
    }

    private val listener : LocationListener = object: LocationListener {
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

        override fun onProviderEnabled(provider: String?) {}

        override fun onProviderDisabled(provider: String?) {}

        @SuppressLint("CheckResult")
        override fun onLocationChanged(location: Location?) {
            if(locationCounter==0) {
                currentLocation =
                    location!!.latitude.toString() + "," + location.longitude.toString()
                latitude = location.latitude
                longitude = location.longitude
                viewModel.googleMaps(
                    currentLocation,
                    4000.344,
                    "restaurant",
                    "AIzaSyBomoHX1r4nKmQb48FQHt4v0WGGc4hsay0",
                    "burrito"
                )
                    .observe(this@MainActivity, object : Observer<PlacesResults> {
                        override fun onChanged(t: PlacesResults?) {
                            if (t != null) {
                                locationCounter++
                                results = t.results
                                adapter = RecyclerViewAdapter(applicationContext, t.results)
                                recyclerView.adapter = adapter
                                adapter.setOnitemClickListener(this@MainActivity)
                            }
                        }
                    })
            }
        }
    }

    private fun getLocationPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            location = getSystemService(LOCATION_SERVICE) as LocationManager
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*1000,1f, listener)
        }else{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, Array(1) {android.Manifest.permission.ACCESS_FINE_LOCATION} , 12)
            }
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, Array(1){Manifest.permission.ACCESS_COARSE_LOCATION} , 13)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        location = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        assert(location != null)
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        } else {
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1f, listener)
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        locationCounter=0
    }
}
