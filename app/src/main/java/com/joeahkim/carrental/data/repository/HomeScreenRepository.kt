package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.domain.model.TopCars

interface HomeScreenRepository {

    suspend fun getAvailableCars(token: String): Result<List<AvailableCars>>
    suspend fun getTopCars(token: String): Result<List<TopCars>>
}