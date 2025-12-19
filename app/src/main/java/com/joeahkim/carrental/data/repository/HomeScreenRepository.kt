package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.domain.model.AvailableCars

interface HomeScreenRepository {

    suspend fun getAvailableCars(token: String): Result<List<AvailableCars>>
}