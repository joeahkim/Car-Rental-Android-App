package com.joeahkim.carrental.domain.usecase

import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.domain.model.Brand
import com.joeahkim.carrental.domain.model.TopCars

data class HomeScreenData(
    val availableCars: List<AvailableCars>,
    val topCars: List<TopCars>,
    val brands: List<Brand>
)
