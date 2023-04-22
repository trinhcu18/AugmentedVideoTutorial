package com.shliama.augmentedvideotutorial.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://6440ab95792fe886a8937ed3.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val myApiService = retrofit.create(MyApiService::class.java)
    val downloadVideoService = retrofit.create(DownloadVideoService::class.java)

}