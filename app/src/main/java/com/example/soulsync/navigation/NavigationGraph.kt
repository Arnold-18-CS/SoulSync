package com.example.soulsync.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.soulsync.StartScreen
import com.example.soulsync.auth.AppHome
import com.example.soulsync.auth.LoginUser
import com.example.soulsync.auth.RegisterUser

private const val TAG = "NavigationGraph"

@Suppress("ktlint:standard:function-naming")
@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.StartScreen.route,
    ) {
        composable(route = AppDestinations.StartScreen.route) {
            Log.d(TAG, "Navigated to: StartScreen")
            StartScreen(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen, from StartScreen")
                    navController.navigate(AppDestinations.LoginScreen.route)
                },
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from StartScreen")
                    navController.navigate(AppDestinations.RegisterScreen.route)
                },
            )
        }

        composable(AppDestinations.RegisterScreen.route) {
            Log.d(TAG, "Navigated to: RegisterScreen")
            RegisterUser(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen, from RegisterScreen")
                    navController.navigate(AppDestinations.LoginScreen.route)
                },
            )
        }

        composable(AppDestinations.LoginScreen.route) {
            Log.d(TAG, "Navigated to: LoginScreen")
            LoginUser(
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from LoginScreen")
                    navController.navigate(AppDestinations.RegisterScreen.route)
                },
                onNavigateToHome = {
                    Log.d(TAG, "Navigated to: HomeScreen, from LoginScreen")
                    navController.navigate(AppDestinations.HomeScreen.route)
                },
            )
        }

        composable(AppDestinations.HomeScreen.route) {
            Log.d(TAG, "Navigated to: HomeScreen")
            AppHome(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen, from HomeScreen")
                    navController.navigate(AppDestinations.LoginScreen.route)
                },
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from HomeScreen")
                    navController.navigate(AppDestinations.RegisterScreen.route)
                },
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from HomeScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }
    }
}
