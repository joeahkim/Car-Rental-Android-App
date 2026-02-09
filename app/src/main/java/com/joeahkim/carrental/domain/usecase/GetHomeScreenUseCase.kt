package com.joeahkim.carrental.domain.usecase

import com.joeahkim.carrental.data.repository.HomeScreenRepository
import com.joeahkim.carrental.domain.model.AvailableCars
import javax.inject.Inject

class GetHomeScreenUseCase @Inject constructor(
    private val repository: HomeScreenRepository
) {
    suspend operator fun invoke(token: String): Result<HomeScreenData> {
        return runCatching {
            val availableCars = repository.getAvailableCars(token).getOrThrow()
            val topCars = repository.getTopCars(token).getOrThrow()
            val brands = repository.getBrands(token).getOrThrow()

            HomeScreenData(
                availableCars = availableCars,
                topCars = topCars,
                brands = brands
            )
        }
    }
}
