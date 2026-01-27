package com.joeahkim.carrental.data.repository

import com.joeahkim.carrental.domain.model.CarDetail
interface CarRepository {
    suspend fun getCarDetails(carId: Int, token: String): Result<CarDetail>
}