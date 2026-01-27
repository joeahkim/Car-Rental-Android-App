package com.joeahkim.carrental.domain.model

data class CarDetail(
    val id: Int,
    val make: String,
    val model: String,
    val pricePerDay: String,
    val numberPlate: String,
    val imageUrl: String?
)