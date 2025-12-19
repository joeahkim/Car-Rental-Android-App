package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.data.remote.api.HomeScreenApi
import javax.inject.Inject

class HomeScreenRepositoryImpl @Inject constructor(
    private val api: HomeScreenApi
) : HomeScreenRepository {
    override suspend fun getAvailableCars(token: String): Result<List<AvailableCars>> = runCatching {
        val dtos = api.getAvailableCars("Bearer $token")
        dtos.map { dto ->
            AvailableCars(
                id = dto.id,
                carName = dto.carName,
                price = dto.price,
                carImageUrl = dto.carImageUrl
            )

        }
    }
}