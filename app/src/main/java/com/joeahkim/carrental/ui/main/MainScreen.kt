package com.joeahkim.carrental.ui.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.joeahkim.carrental.navigation.Routes
import com.joeahkim.carrental.navigation.Screen
import com.joeahkim.carrental.ui.bookings.BookingsScreen
import com.joeahkim.carrental.ui.cardetails.CarDetailsScreen
import com.joeahkim.carrental.ui.home.HomeScreen
import com.joeahkim.carrental.ui.profile.ProfileScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bookings,
        BottomNavItem.Profile
    )

    val isOnCarDetails = currentDestination?.route?.contains("car-details") == true

    Box(modifier = Modifier.fillMaxSize()) {

        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.fillMaxSize(),
            enterTransition = {
                if (targetState.destination.route?.contains("car-details") == true) {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                } else {
                    EnterTransition.None
                }
            },
            exitTransition = {
                if (targetState.destination.route?.contains("car-details") == true) {
                    fadeOut(animationSpec = tween(300))
                } else {
                    ExitTransition.None
                }
            },
            popEnterTransition = {
                if (initialState.destination.route?.contains("car-details") == true) {
                    fadeIn(animationSpec = tween(300))
                } else {
                    EnterTransition.None
                }
            },
            popExitTransition = {
                if (initialState.destination.route?.contains("car-details") == true) {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                } else {
                    ExitTransition.None
                }
            }
        ) {

            composable<Routes.Home> {
                HomeScreen(
                    onCarClick = { carId ->
                        navController.navigate(Screen.CarDetails.createRoute(carId))
                    }
                )
            }

            composable<Routes.Bookings> {
                BookingsScreen(
                    onCarClick = { carId ->
                        navController.navigate(Screen.CarDetails.createRoute(carId))
                    }
                )
            }

            composable<Routes.Profile> {
                ProfileScreen()
            }

            composable(
                route = Screen.CarDetails.route,
                arguments = listOf(navArgument("carId") {
                    type = NavType.IntType
                })
            ) { backStackEntry ->

                val carId = backStackEntry.arguments?.getInt("carId") ?: 0

                CarDetailsScreen(
                    carId = carId,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        AnimatedVisibility(
            visible = !isOnCarDetails,
            enter = slideInVertically { it } + fadeIn(),
            exit = slideOutVertically { it } + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFF0A0E21),
                    tonalElevation = 8.dp,
                    shadowElevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                ) {
                    NavigationBar(
                        containerColor = Color.Transparent,
                        tonalElevation = 0.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        bottomNavItems.forEach { item ->
                            val isSelected = currentDestination?.route?.let { route ->
                                when (item.route) {
                                    is Routes.Home -> route.contains("Home", ignoreCase = true)
                                    is Routes.Bookings -> route.contains("Bookings", ignoreCase = true)
                                    is Routes.Profile -> route.contains("Profile", ignoreCase = true)
                                    else -> false
                                }
                            } ?: false

                            NavigationBarItem(
                                selected = isSelected,
                                onClick = {
                                    navController.navigate(item.route) {
                                        popUpTo<Routes.Home> {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.title,
                                        tint = if (isSelected) Color.White else Color.Gray
                                    )
                                },
                                label = {
                                    if (isSelected) {
                                        Text(
                                            text = item.title,
                                            color = Color.White,
                                            fontSize = 12.sp
                                        )
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent,
                                    selectedIconColor = Color.White,
                                    unselectedIconColor = Color.Gray,
                                    selectedTextColor = Color.White,
                                    unselectedTextColor = Color.Gray
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}