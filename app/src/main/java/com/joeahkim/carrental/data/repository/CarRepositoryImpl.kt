package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.data.remote.api.CarDetailsApi
import com.joeahkim.carrental.domain.model.CarDetail
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val api: CarDetailsApi
) : CarRepository {

    override suspend fun getCarDetails(carId: Int, token: String): Result<CarDetail> = runCatching {
        val dto = api.getCarDetails(carId, "Bearer $token")
        CarDetail(
            id = dto.id,
            make = dto.make,
            model = dto.model,
            pricePerDay = dto.pricePerDay,
            numberPlate = dto.numberPlate,
            imageUrl = dto.imageUrl
        )
    }
}