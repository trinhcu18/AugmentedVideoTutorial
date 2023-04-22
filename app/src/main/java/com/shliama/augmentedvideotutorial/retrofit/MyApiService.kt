package com.shliama.augmentedvideotutorial.retrofit

import com.shliama.augmentedvideotutorial.model.UploadFileServerResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface MyApiService {
    @Multipart
    @POST("files")
    suspend fun uploadIFileToServer(
        @Part image : MultipartBody.Part,
        @Part video : MultipartBody.Part,
        @Part("folderId")  folderId : RequestBody
    ) : UploadFileServerResponse
}