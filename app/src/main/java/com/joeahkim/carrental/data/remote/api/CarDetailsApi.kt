package com.joeahkim.carrental.data.remote.api
import com.joeahkim.carrental.data.remote.dto.CarDetailDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CarDetailsApi {
    @GET("api/v1/cars/{id}")
    suspend fun getCarDetails(
        @Path("id") carId: Int,
        @Header("Authorization") token: String
    ): CarDetailDto
}