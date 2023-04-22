package com.shliama.augmentedvideotutorial.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface DownloadVideoService {
    @GET("anything")
    suspend fun getData(@Url videoURL : String) : ResponseBody
    }