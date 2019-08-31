package com.mycode.currentapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlacesResults : Serializable{
    @SerializedName("results")
    var results : MutableList<Result> = mutableListOf()
}
