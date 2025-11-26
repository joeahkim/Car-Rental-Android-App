package com.joeahkim.carrental.ui.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.joeahkim.carrental.navigation.Routes

data class BottomNavItem(
    val route: Routes,
    val title: String,
    val icon: ImageVector
) {
    companion object {
        val Home = BottomNavItem(Routes.Home, "Home", Icons.Default.Home)
        val Bookings = BottomNavItem(Routes.Bookings, "Bookings", Icons.Default.DateRange)
        val Profile = BottomNavItem(Routes.Profile, "Profile", Icons.Default.Person)

        val items = listOf(Home, Bookings, Profile)
    }
}