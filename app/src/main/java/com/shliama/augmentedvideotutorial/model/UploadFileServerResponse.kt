package com.shliama.augmentedvideotutorial.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class UploadFileServerResponse {
    @SerializedName("id")
    @Expose
    private val id: String? = null

    @SerializedName("imageUrl")
    @Expose
    private val imageUrl: String? = null

    @SerializedName("videoUrl")
    @Expose
    private val videoUrl: String? = null

    @SerializedName("folderId")
    @Expose
    private val folderId: String? = null
}