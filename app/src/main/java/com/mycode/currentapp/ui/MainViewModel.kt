package com.mycode.currentapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mycode.currentapp.entities.PlacesResults
import com.mycode.currentapp.repository.Repository
import javax.inject.Inject

class MainViewModel @Inject constructor(private var repository: Repository) : ViewModel() {

    fun googleMaps(location : String, radius: Double, type : String, key : String, keyword : String) : LiveData<PlacesResults> {
        return repository.googleMapsAPI(location, radius, type, key,keyword)
    }
}