package com.joeahkim.carrental.domain.model

data class AvailableCars(
    val id : String,
    val carName : String,
    val price : String,
    val carImageUrl : String?
)