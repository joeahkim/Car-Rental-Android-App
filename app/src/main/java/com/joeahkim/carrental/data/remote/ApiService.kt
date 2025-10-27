package com.joeahkim.carrental.data.remote

import com.joeahkim.carrental.data.model.LoginRequest
import com.joeahkim.carrental.data.model.LoginResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("Content-Type: application/json")
    @POST("api/v1/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}