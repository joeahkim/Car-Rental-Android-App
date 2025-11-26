// ui/main/MainScreen.kt
package com.joeahkim.carrental.ui.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joeahkim.carrental.navigation.Routes
import com.joeahkim.carrental.ui.bookings.BookingsScreen
import com.joeahkim.carrental.ui.home.HomeScreen
import com.joeahkim.carrental.ui.profile.ProfileScreen
import com.joeahkim.carrental.ui.main.BottomNavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Now this works perfectly
    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bookings,
        BottomNavItem.Profile
    )

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF0A0E21)) {
                bottomNavItems.forEach { item ->
                    val selected = currentDestination?.hierarchy?.any {
                        it.hasRoute(item.route::class)
                    } == true

                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = if (selected) Color.White else Color.Gray
                            )
                        },
                        label = {
                            Text(
                                text = item.title,
                                color = if (selected) Color.White else Color.Gray,
                                fontSize = 12.sp
                            )
                        },
                        alwaysShowLabel = true
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Routes.Home> { HomeScreen() }
            composable<Routes.Bookings> { BookingsScreen() }
            composable<Routes.Profile> { ProfileScreen() }
        }
    }
}