package com.example.soulsync.navigation

sealed class HomeDestinations(
    val route: String,
    val title: String,
    val icon: String,
) {
    object Home : HomeDestinations("home", "Home", "Home")

    object Quotes : HomeDestinations("quotes", "Quotes", "Checklist")

    object Memories : HomeDestinations("memories", "Memories", "Image")

    object Outbox : HomeDestinations("outbox", "Outbox", "Outbox")

    object Profile : HomeDestinations("profile", "Profile", "Person")
}
