package com.shliama.augmentedvideotutorial.retrofit

import retrofit2.http.GET

interface MyApiService {
    @GET("imageTarget")
    suspend fun getImage(): MyResponse
}