package com.example.soulsync.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Suppress("ktlint:standard:function-naming")
@Composable
fun AppHome(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToStart: () -> Unit = {},
) {
    Text(text = "Home Screen")
}
