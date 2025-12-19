package com.joeahkim.carrental.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class HomeScreenDto(
    val id : String,
    val carName : String,
    val price : String,
    val carImageUrl : String?
)