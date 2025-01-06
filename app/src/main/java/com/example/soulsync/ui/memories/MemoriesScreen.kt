package com.example.soulsync.ui.memories

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.auth.AuthViewModel
import com.example.soulsync.ui.theme.BackgroundImage
import com.example.soulsync.ui.theme.SSPrimaryButton

@Suppress("ktlint:standard:function-naming")
@Composable
fun MemoriesScreen(onNavigateToStart: () -> Unit = {}) {
    val authViewModel: AuthViewModel = hiltViewModel()
    BackgroundImage.Background {
        Column {
            Text(text = "Memories Screen")

            Spacer(modifier = Modifier.height(16.dp))

            SSPrimaryButton(
                text = "Logout",
                onClick = {
                    authViewModel.logout()
                    Log.d("AppHome", "User logged out")
                    onNavigateToStart()
                },
            )
        }
    }
}
