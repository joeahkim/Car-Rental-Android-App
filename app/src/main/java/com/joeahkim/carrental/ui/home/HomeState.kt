package com.joeahkim.carrental.ui.home

import com.joeahkim.carrental.domain.model.AvailableCars
import com.joeahkim.carrental.domain.model.TopCars

data class HomeState (
    val isLoading: Boolean = false,
    val availableCars: List<AvailableCars> = emptyList(),
    val topCars: List<TopCars> = emptyList(),
    val error: String? = null
)