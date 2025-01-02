package com.example.soulsync.navigation

sealed class AppDestinations(
    val route: String,
) {
    object MainSplashScreen : AppDestinations("splashScreen")

    object StartScreen : AppDestinations("startScreen")

    object LoginScreen : AppDestinations("loginScreen")

    object RegisterScreen : AppDestinations("registerScreen")

    object HomeScreen : AppDestinations("homeScreen")
}
