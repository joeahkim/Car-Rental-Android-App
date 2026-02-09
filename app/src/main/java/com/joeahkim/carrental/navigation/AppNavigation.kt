// navigation/AppNavigation.kt
package com.joeahkim.carrental.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joeahkim.carrental.data.local.DataStoreManager
import com.joeahkim.carrental.ui.main.MainScreen
import com.joeahkim.carrental.ui.login.LoginScreen
import com.joeahkim.carrental.ui.login.SignUpScreen
import kotlinx.coroutines.flow.firstOrNull

@Composable
fun AppNavigation() {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    val startDestination = remember { mutableStateOf<Routes?>(null) }

    LaunchedEffect(Unit) {
        val token = dataStoreManager.getToken().firstOrNull()
        startDestination.value = if (!token.isNullOrEmpty()) Routes.Main else Routes.Login
    }

    val destination = startDestination.value ?: return

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = destination
    ) {
        composable<Routes.Login> {
            LoginScreen(
                onSignUpClick = { navController.navigate(Routes.SignUp) },
                onLoginSuccess = {
                    navController.navigate(Routes.Main) {
                        popUpTo<Routes.Login> { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable<Routes.SignUp> {
            SignUpScreen(
                onLoginClick = { navController.navigate(Routes.Login) }
            )
        }
        composable<Routes.Main> {
            MainScreen()
        }
    }
}