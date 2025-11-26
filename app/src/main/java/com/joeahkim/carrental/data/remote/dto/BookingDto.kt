package com.joeahkim.carrental.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BookingDto(
    val id: String,
    val car_name: String,
    val car_image_url: String? = null,
    val pickup_date: String,
    val return_date: String,
    val total_price: String,
    val status: String
)