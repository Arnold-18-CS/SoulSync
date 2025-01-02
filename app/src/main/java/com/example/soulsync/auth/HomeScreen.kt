package com.example.soulsync.auth

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.soulsync.ui.theme.BackgroundImage

@Suppress("ktlint:standard:function-naming")
@Composable
fun AppHome(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
    onNavigateToStart: () -> Unit = {},
) {
    BackgroundImage.Background {
        Text(text = "Home Screen")
    }
}
