package com.mycode.currentapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Result : Serializable{

    @SerializedName("name")
    var name: String = ""

    @SerializedName("vicinity")
    var address: String = ""

    @SerializedName("rating")
    var rating: String = ""

    @SerializedName("price_level")
    var price: String = ""

    @SerializedName("geometry")
    var geometry: Geometry? = null

    var priceSign = ""

}