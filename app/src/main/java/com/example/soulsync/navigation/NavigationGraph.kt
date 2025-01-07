package com.example.soulsync.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.soulsync.SplashScreen
import com.example.soulsync.StartScreen
import com.example.soulsync.auth.LoginUser
import com.example.soulsync.auth.RegisterUser
import com.example.soulsync.ui.home.AppHome
import com.example.soulsync.ui.memories.Memories
import com.example.soulsync.ui.outbox.Outbox
import com.example.soulsync.ui.quotes.Quotes

private const val TAG = "NavigationGraph"

@Suppress("ktlint:standard:function-naming")
@Composable
fun NavigationGraph(
    navController: NavHostController,
    isBottomBarVisible: (Boolean) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.MainSplashScreen.route,
    ) {
        composable(route = AppDestinations.MainSplashScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: SplashScreen")
            SplashScreen(navController = navController)
        }

        composable(route = AppDestinations.StartScreen.route) {
            isBottomBarVisible(false)
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
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: RegisterScreen")
            RegisterUser(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen, from RegisterScreen")
                    navController.navigate(AppDestinations.LoginScreen.route)
                },
            )
        }

        composable(AppDestinations.LoginScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: LoginScreen")
            LoginUser(
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from LoginScreen")
                    navController.navigate(AppDestinations.RegisterScreen.route)
                },
                onNavigateToHome = {
                    Log.d(TAG, "Navigated to: HomeScreen, from LoginScreen")
                    navController.navigate(AppDestinations.HomeScreen.route) {
                        popUpTo(AppDestinations.LoginScreen.route) { inclusive = true }
                    }
                },
            )
        }

        composable(AppDestinations.HomeScreen.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: HomeScreen")
            AppHome(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from HomeScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Home.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: HomeScreen")
            AppHome(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from HomeScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Quotes.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Quotes Screen")
            Quotes(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from QuotesScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Memories.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Memories Screen")
            Memories(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from MemoriesScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Outbox.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Outbox Screen")
            Outbox(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from OutboxScreen")
                    navController.navigate(AppDestinations.StartScreen.route)
                },
            )
        }
    }
}
