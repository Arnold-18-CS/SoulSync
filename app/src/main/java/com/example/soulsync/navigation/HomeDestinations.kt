package com.example.soulsync.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Outbox
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class HomeDestinations(
    val route: String,
    val title: String? = null,
    val icon: ImageVector? = null,
) {
    object Home : HomeDestinations("home", "Home", Icons.Filled.Home)

    object Quotes : HomeDestinations("quotes", "Quotes", Icons.Filled.Checklist)

    object Memories : HomeDestinations("memories", "Memories", Icons.Filled.Image)

    object Outbox : HomeDestinations("outbox", "Outbox", Icons.Filled.Outbox)

    object Settings : HomeDestinations("settings", "Settings", Icons.Filled.Settings)
}
