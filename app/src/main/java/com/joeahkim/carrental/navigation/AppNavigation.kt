// navigation/AppNavigation.kt
package com.joeahkim.carrental.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joeahkim.carrental.ui.main.MainScreen
import com.joeahkim.carrental.ui.login.LoginScreen
import com.joeahkim.carrental.ui.login.SignUpScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login
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