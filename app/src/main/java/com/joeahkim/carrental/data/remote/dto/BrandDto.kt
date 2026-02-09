package com.joeahkim.carrental.data.remote.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BrandDto(
    val id: Int,
    val name: String,
    @SerializedName("logoUrl")
    val logoUrl: String?
)
