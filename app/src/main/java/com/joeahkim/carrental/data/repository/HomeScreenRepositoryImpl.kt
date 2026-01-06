package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.data.remote.api.HomeScreenApi
import com.joeahkim.carrental.domain.model.TopCars
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

    override suspend fun getTopCars(token: String): Result<List<TopCars>> = runCatching {
        api.getTopCars("Bearer $token").map { dto ->
            TopCars(
                id = dto.id,
                name = dto.name,
                pricePerDay = dto.pricePerDay,
                imageUrl = dto.imageUrl,
                bookingsCount = dto.bookingsCount
            )
        }
    }
}