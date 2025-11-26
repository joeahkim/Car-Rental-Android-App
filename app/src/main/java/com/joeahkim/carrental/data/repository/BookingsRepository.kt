package com.joeahkim.carrental.data.repository

import com.google.gson.stream.JsonToken
import com.joeahkim.carrental.domain.model.Booking

interface BookingsRepository {
    suspend fun getBookings(token: String): Result<List<Booking>>
}