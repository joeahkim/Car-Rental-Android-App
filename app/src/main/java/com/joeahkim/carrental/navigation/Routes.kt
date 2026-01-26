package com.joeahkim.carrental.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {
    @Serializable data object Login : Routes()
    @Serializable data object SignUp : Routes()
    @Serializable data object Main : Routes()
    @Serializable data object Home : Routes()
    @Serializable data object Bookings : Routes()
    @Serializable data object Profile : Routes()
}