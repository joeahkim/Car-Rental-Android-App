package com.joeahkim.carrental.domain.model

data class TopCars(
    val id: Int,
    val name: String,
    val pricePerDay: String,
    val imageUrl: String?,
    val bookingsCount: Int
)
