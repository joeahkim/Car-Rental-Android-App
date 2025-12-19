package com.joeahkim.carrental.domain.usecase

import com.joeahkim.carrental.data.repository.HomeScreenRepository
import com.joeahkim.carrental.domain.model.AvailableCars
import javax.inject.Inject

class GetHomeScreenUseCase @Inject constructor(
    private val homeScreenRepository: HomeScreenRepository
){
    suspend operator fun invoke(token: String): Result<List<AvailableCars>>{
        return homeScreenRepository.getAvailableCars(token)
    }
}