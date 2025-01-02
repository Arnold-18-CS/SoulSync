package com.example.soulsync.navigation

sealed class HomeDestinations(
    val route: String,
) {
    object Home : HomeDestinations("home")

    object Quotes : HomeDestinations("quotes")

    object Memories : HomeDestinations("memories")

    object Outbox : HomeDestinations("outbox")

    object Profile : HomeDestinations("profile")
}
