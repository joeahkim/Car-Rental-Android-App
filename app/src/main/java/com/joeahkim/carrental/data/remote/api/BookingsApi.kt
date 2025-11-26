package com.joeahkim.carrental.data.remote.api

import com.joeahkim.carrental.data.remote.dto.BookingDto
import retrofit2.http.GET
import retrofit2.http.Header

interface BookingsApi {
    @GET("Bookings")
    suspend fun getBookings(@Header("Authorization") token: String): List<BookingDto>
}