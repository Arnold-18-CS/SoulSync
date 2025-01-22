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
import com.example.soulsync.auth.ResetPassword
import com.example.soulsync.auth.VerifyEmail
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
        startDestination = AuthDestinations.MainSplashScreen.route,
    ) {
        composable(route = AuthDestinations.MainSplashScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: SplashScreen")
            SplashScreen(navController = navController)
        }

        composable(route = AuthDestinations.StartScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: StartScreen")
            StartScreen(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen, from StartScreen")
                    navController.navigate(AuthDestinations.LoginScreen.route)
                },
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from StartScreen")
                    navController.navigate(AuthDestinations.RegisterScreen.route)
                },
            )
        }

        composable(AuthDestinations.RegisterScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: RegisterScreen")
            RegisterUser(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: Login Screen, from RegisterScreen")
                    navController.navigate(AuthDestinations.LoginScreen.route)
                },
                onNavigateToEmailVerify = {
                    Log.d(TAG, "Navigated to: Email Verification, from RegisterScreen")
                    navController.navigate(AuthDestinations.EmailVerificationScreen.route)
                },
            )
        }

        composable(AuthDestinations.EmailVerificationScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: Email Verification Screen")
            VerifyEmail(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen from EmailVerificationScreen")
                    navController.navigate(AuthDestinations.LoginScreen.route)
                },
            )
        }

        composable(AuthDestinations.LoginScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: LoginScreen")
            LoginUser(
                onNavigateToRegister = {
                    Log.d(TAG, "Navigated to: RegisterScreen, from LoginScreen")
                    navController.navigate(AuthDestinations.RegisterScreen.route)
                },
                onNavigateToHome = {
                    Log.d(TAG, "Navigated to: HomeScreen, from LoginScreen")
                    navController.navigate(AuthDestinations.HomeScreen.route) {
                        popUpTo(AuthDestinations.LoginScreen.route) { inclusive = true }
                    }
                },
            )
        }

        composable(AuthDestinations.PasswordResetScreen.route) {
            isBottomBarVisible(false)
            Log.d(TAG, "Navigated to: Password Reset Screen")
            ResetPassword(
                onNavigateToLogin = {
                    Log.d(TAG, "Navigated to: LoginScreen from PasswordResetScreen")
                    navController.navigate(AuthDestinations.LoginScreen.route)
                },
            )
        }

        composable(AuthDestinations.HomeScreen.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: HomeScreen")
            AppHome(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from HomeScreen")
                    navController.navigate(AuthDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Home.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: HomeScreen")
            AppHome(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from HomeScreen")
                    navController.navigate(AuthDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Quotes.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Quotes Screen")
            Quotes(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from QuotesScreen")
                    navController.navigate(AuthDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Memories.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Memories Screen")
            Memories(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from MemoriesScreen")
                    navController.navigate(AuthDestinations.StartScreen.route)
                },
            )
        }

        composable(HomeDestinations.Outbox.route) {
            isBottomBarVisible(true)
            Log.d(TAG, "Navigated to: Outbox Screen")
            Outbox(
                onNavigateToStart = {
                    Log.d(TAG, "Navigated to: StartScreen, from OutboxScreen")
                    navController.navigate(AuthDestinations.StartScreen.route)
                },
            )
        }
    }
}
