package com.mycode.currentapp.entities

import com.google.gson.annotations.SerializedName
import org.xml.sax.Locator
import java.io.Serializable

class Geometry : Serializable {

    @SerializedName("location")
    var location : Location? = null
}