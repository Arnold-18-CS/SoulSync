package com.example.soulsync.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.soulsync.StartScreen
import com.example.soulsync.auth.LoginUser
import com.example.soulsync.auth.RegisterUser

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.StartScreen.route
    ) {
        composable(AppDestinations.StartScreen.route) {
            StartScreen(
                onNavigateToLogin = { navController.navigate(AppDestinations.LoginScreen.route) },
                onNavigateToRegister = { navController.navigate(AppDestinations.RegisterScreen.route) }
            )
        }

        composable(AppDestinations.LoginScreen.route) {
            LoginUser(
                onNavigateToRegister = { navController.navigate(AppDestinations.RegisterScreen.route) }
            )
        }

        composable(AppDestinations.RegisterScreen.route) {
            RegisterUser(
                onNavigateToLogin = { navController.navigate(AppDestinations.LoginScreen.route) }
            )
        }

    }
}