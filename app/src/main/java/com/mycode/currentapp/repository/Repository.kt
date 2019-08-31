package com.mycode.currentapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.mycode.currentapp.entities.PlacesResults
import com.mycode.currentapp.network.GoogleMapsAPI
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

class Repository @Inject constructor(private var googleMapsAPI: GoogleMapsAPI) {

    fun googleMapsAPI(location : String, radius: Double, type : String, key : String, keyword : String) : LiveData<PlacesResults>{
        return LiveDataReactiveStreams.fromPublisher(
            googleMapsAPI.getNearby(location, radius, type, key, keyword).subscribeOn(Schedulers.io())
        )
    }
}