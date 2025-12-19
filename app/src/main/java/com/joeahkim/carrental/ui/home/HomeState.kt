package com.joeahkim.carrental.ui.home

import com.joeahkim.carrental.domain.model.AvailableCars

data class HomeState (
    val isLoading: Boolean = false,
    val availableCars: List<AvailableCars> = emptyList(),
    val error: String? = null
)