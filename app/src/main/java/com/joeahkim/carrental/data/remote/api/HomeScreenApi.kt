package com.joeahkim.carrental.data.remote.api

import com.joeahkim.carrental.data.remote.dto.BrandDto
import com.joeahkim.carrental.data.remote.dto.HomeScreenDto
import com.joeahkim.carrental.data.remote.dto.TopCarsDto
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeScreenApi {
    @GET("api/v1/homescreen")
    suspend fun getAvailableCars(@Header("Authorization") token: String): List<HomeScreenDto>

    @GET("api/v1/homescreen/topcars")
    suspend fun getTopCars(
        @Header("Authorization") token: String): List<TopCarsDto>

    @GET("api/v1/cars/brands")
    suspend fun getBrands(@Header("Authorization") token: String): List<BrandDto>
}