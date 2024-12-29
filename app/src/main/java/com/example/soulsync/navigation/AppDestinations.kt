package com.example.soulsync.navigation

sealed class AppDestinations(val route: String) {
    object StartScreen : AppDestinations("startScreen")
    object LoginScreen : AppDestinations("loginScreen")
    object RegisterScreen : AppDestinations("registerScreen")
}