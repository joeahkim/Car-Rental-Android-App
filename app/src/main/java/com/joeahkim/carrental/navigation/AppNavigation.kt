package com.joeahkim.carrental.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.joeahkim.carrental.ui.login.LoginScreen
import com.joeahkim.carrental.ui.login.SignUpScreen

object Routes {
    const val LOGIN = "login"
    const val SIGNUP = "signup"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                onSignUpClick = { navController.navigate(Routes.SIGNUP) }
            )
        }
        composable(Routes.SIGNUP) {
            SignUpScreen(
                onLoginClick = { navController.navigate(Routes.LOGIN) }
            )
        }
    }
}