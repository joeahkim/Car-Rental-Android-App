package com.joeahkim.carrental.data.remote

import com.joeahkim.carrental.data.model.Client
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header

interface ProfileApiService {
    @GET("api/v1/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): Client

    @GET("api/v1/profile")
    suspend fun getProfileRaw(
        @Header("Authorization") token: String
    ): ResponseBody

}