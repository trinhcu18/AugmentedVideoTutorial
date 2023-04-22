package com.shliama.augmentedvideotutorial.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class OwnerResponse (
    @SerializedName("id")
    @Expose
    val id: String? = null,

    @SerializedName("ownerId")
    @Expose
    val ownerId: String? = null,

    @SerializedName("imageTargets")
    @Expose
    val imageTargets: List<ImageTarget>? = null
)