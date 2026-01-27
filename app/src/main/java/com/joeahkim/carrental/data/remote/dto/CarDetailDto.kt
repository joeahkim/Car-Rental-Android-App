package com.joeahkim.carrental.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CarDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("make")
    val make: String,
    @SerializedName("model")
    val model: String,
    @SerializedName("pricePerDay")
    val pricePerDay: String,
    @SerializedName("numberPlate")
    val numberPlate: String,
    @SerializedName("imageUrl")
    val imageUrl: String?
)
