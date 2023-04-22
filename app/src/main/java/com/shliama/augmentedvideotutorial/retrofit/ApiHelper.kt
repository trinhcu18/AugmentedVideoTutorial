package com.shliama.augmentedvideotutorial.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    val BASE_URL = "http://13.229.84.118:3000/"
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApiService = retrofit.create(MyApiService::class.java)
    val downloadVideoService = retrofit.create(DownloadVideoService::class.java)

}