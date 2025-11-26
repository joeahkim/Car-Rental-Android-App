package com.joeahkim.carrental.domain.usecase

import com.joeahkim.carrental.data.repository.BookingsRepository
import com.joeahkim.carrental.domain.model.Booking
import javax.inject.Inject

class GetBookingsUseCase @Inject constructor(
    private val bookingsRepository: BookingsRepository
){
    suspend operator fun invoke(token: String): Result<List<Booking>>{
        return bookingsRepository.getBookings(token)
    }
}