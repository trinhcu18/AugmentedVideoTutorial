package com.shliama.augmentedvideotutorial.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class ImageTarget (
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("imageUrl")
    @Expose
    val imageUrl: String? = null,

    @SerializedName("videoUrl")
    @Expose
    val videoUrl: String? = null,

    @SerializedName("folderId")
    @Expose
    val folderId: String? = null
)