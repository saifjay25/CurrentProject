package com.mycode.currentapp.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mycode.currentapp.network.GoogleMapsAPI
import com.mycode.currentapp.repository.Repository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @JvmStatic
    @Provides
    fun providesGson() : Gson = GsonBuilder().create()

    @Singleton
    @JvmStatic
    @Provides
    fun provideRetrofitInstance(gson : Gson, client : OkHttpClient) : Retrofit
    {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun providesHTTPClient() :OkHttpClient
    {
        val builder = OkHttpClient.Builder()
        return builder.build()
    }

    @Provides
    @JvmStatic
    fun provideMainAPI (retrofit : Retrofit): GoogleMapsAPI
    {
        return retrofit.create(GoogleMapsAPI::class.java)
    }

    @JvmStatic
    @Provides
    fun provideRepository(googleMapsAPI: GoogleMapsAPI): Repository {
        return Repository(googleMapsAPI)
    }
}
