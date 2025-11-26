package com.joeahkim.carrental.domain.model

data class Booking(
    val id: String,
    val carName: String,
    val pickupDate: String,
    val returnDate: String,
    val totalPrice: String,
    val status: BookingStatus,
    val carImageUrl: String?
)

enum class BookingStatus{
    UPCOMING, ONGOING, COMPLETED, CANCELLED
}