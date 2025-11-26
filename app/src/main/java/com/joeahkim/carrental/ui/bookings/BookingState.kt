package com.joeahkim.carrental.ui.bookings

import com.joeahkim.carrental.domain.model.Booking

data class BookingsState(
    val isLoading: Boolean = false,
    val bookings: List<Booking> = emptyList(),
    val error: String? = null
)