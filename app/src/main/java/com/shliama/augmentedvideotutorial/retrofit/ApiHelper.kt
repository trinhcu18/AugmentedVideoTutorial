package com.shliama.augmentedvideotutorial.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://13.229.84.118:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApiService = retrofit.create(MyApiService::class.java)

}