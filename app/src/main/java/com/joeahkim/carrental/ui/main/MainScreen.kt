package com.joeahkim.carrental.ui.main

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

    val showBottomBar = currentDestination?.route?.contains("car-details") != true

    var showBottomBarDelayed by remember { mutableStateOf(showBottomBar) }
    var isTransitioning by remember { mutableStateOf(false) }

    LaunchedEffect(showBottomBar) {
        if (showBottomBar) {
            isTransitioning = true
            kotlinx.coroutines.delay(300)
            showBottomBarDelayed = true
            isTransitioning = false
        } else {
            showBottomBarDelayed = false
            isTransitioning = false
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBarDelayed) {
                AnimatedVisibility(
                    visible = showBottomBarDelayed,
                    enter = slideInVertically(
                        initialOffsetY = { it },
                        animationSpec = tween(durationMillis = 200)
                    ) + fadeIn(animationSpec = tween(durationMillis = 200)),
                    exit = slideOutVertically(
                        targetOffsetY = { it },
                        animationSpec = tween(durationMillis = 200)
                    ) + fadeOut(animationSpec = tween(durationMillis = 200))
                ) {
                    NavigationBar(containerColor = Color(0xFF0A0E21)) {
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
                                    Text(
                                        text = item.title,
                                        color = if (isSelected) Color.White else Color.Gray,
                                        fontSize = 12.sp
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    ) { padding ->

        if (isTransitioning) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        NavHost(
            navController = navController,
            startDestination = Routes.Home,
            modifier = Modifier.padding(padding),
            enterTransition = {
                if (targetState.destination.route?.contains("car-details") == true) {
                    slideInHorizontally(
                        initialOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                } else {
                    fadeIn(animationSpec = tween(200))
                }
            },
            exitTransition = {
                if (initialState.destination.route?.contains("car-details") == true) {
                    slideOutHorizontally(
                        targetOffsetX = { it },
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                } else {
                    fadeOut(animationSpec = tween(200))
                }
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it / 3 },
                    animationSpec = tween(300)
                ) + fadeIn(animationSpec = tween(300))
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(200)
                ) + fadeOut(animationSpec = tween(200))
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
    }
}