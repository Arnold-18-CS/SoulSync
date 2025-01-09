package com.example.soulsync.navigation

sealed class AppDestinations(
    val route: String,
) {
    object MainSplashScreen : AppDestinations("splashScreen")

    object StartScreen : AppDestinations("startScreen")

    object RegisterScreen : AppDestinations("registerScreen")

    object EmailVerificationScreen : AppDestinations("emailVerificationScreen")

    object LoginScreen : AppDestinations("loginScreen")

    object HomeScreen : AppDestinations("homeScreen")
}
