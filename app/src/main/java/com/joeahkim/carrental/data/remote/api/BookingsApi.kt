package com.joeahkim.carrental.data.remote.api

import com.joeahkim.carrental.data.remote.dto.BookingDto
import retrofit2.http.GET
import retrofit2.http.Header

interface BookingsApi {
    @GET("api/v1/bookings")
    suspend fun getBookings(@Header("Authorization") token: String): List<BookingDto>
}