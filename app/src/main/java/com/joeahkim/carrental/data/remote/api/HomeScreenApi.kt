package com.joeahkim.carrental.data.remote.api

import com.joeahkim.carrental.data.remote.dto.HomeScreenDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeScreenApi {
    @GET("api/v1/homescreen")
    suspend fun getAvailableCars(@Header("Authorization") token: String): List<HomeScreenDto>
}