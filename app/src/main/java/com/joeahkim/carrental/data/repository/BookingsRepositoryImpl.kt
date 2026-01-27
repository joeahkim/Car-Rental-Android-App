package com.joeahkim.carrental.data.repository


import com.joeahkim.carrental.data.remote.api.BookingsApi
import com.joeahkim.carrental.domain.model.Booking
import com.joeahkim.carrental.domain.model.BookingStatus
import javax.inject.Inject

class BookingsRepositoryImpl @Inject constructor(
    private val api: BookingsApi
) : BookingsRepository {

    override suspend fun getBookings(token: String): Result<List<Booking>> = runCatching {
        val dtos = api.getBookings("Bearer $token")
        dtos.map { dto ->
            Booking(
                id = dto.id,
                carName = dto.car_name,
                carId = dto.carId,
                carImageUrl = dto.car_image_url,
                pickupDate = dto.pickup_date,
                returnDate = dto.return_date,
                totalPrice = dto.total_price,
                status = when (dto.status.uppercase()) {
                    "UPCOMING" -> BookingStatus.UPCOMING
                    "ONGOING" -> BookingStatus.ONGOING
                    "COMPLETED" -> BookingStatus.COMPLETED
                    "CANCELLED" -> BookingStatus.CANCELLED
                    else -> BookingStatus.COMPLETED
                }
            )
        }
    }
}