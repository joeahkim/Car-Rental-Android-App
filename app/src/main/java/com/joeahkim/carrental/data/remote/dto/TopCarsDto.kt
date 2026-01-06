package com.joeahkim.carrental.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class TopCarsDto(
    val id: Int,
    val name: String,
    val pricePerDay: String,
    val imageUrl: String?,
    val bookingsCount: Int
)
