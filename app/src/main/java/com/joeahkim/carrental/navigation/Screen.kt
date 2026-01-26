package com.joeahkim.carrental.navigation

sealed class Screen(val route: String) {

    object CarDetails : Screen("car-details/{carId}") {
        fun createRoute(carId: Int): String {
            return "car-details/$carId"
        }
    }
}
