package com.mycode.currentapp.network

import com.mycode.currentapp.entities.PlacesResults
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleMapsAPI {
    @GET("place/nearbysearch/json")
    fun getNearby(
        @Query("location") location: String,
        @Query("radius") radius: Double,
        @Query("type") type: String,
        @Query("key") key: String,
        @Query("keyword") keyword : String
    ) : Flowable<PlacesResults>
}
