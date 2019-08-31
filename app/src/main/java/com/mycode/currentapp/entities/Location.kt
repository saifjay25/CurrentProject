package com.mycode.currentapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Location : Serializable {

    @SerializedName("lat")
    var lat: Double = 0.0

    @SerializedName("lng")
    var lon: Double = 0.0
}