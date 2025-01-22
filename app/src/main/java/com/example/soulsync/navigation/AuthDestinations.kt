package com.example.soulsync.navigation

sealed class AuthDestinations(
    val route: String,
) {
    object MainSplashScreen : AuthDestinations("splashScreen")

    object StartScreen : AuthDestinations("startScreen")

    object RegisterScreen : AuthDestinations("registerScreen")

    object EmailVerificationScreen : AuthDestinations("emailVerificationScreen")

    object LoginScreen : AuthDestinations("loginScreen")

    object PasswordResetScreen : AuthDestinations("passwordResetScreen")

    object HomeScreen : AuthDestinations("homeScreen")
}
