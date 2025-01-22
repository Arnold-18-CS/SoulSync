package com.example.soulsync.auth

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.ui.theme.AppColors
import com.example.soulsync.ui.theme.BackgroundImage
import com.example.soulsync.ui.theme.DefaultTextField
import com.example.soulsync.ui.theme.SSSecondaryButton
import kotlinx.coroutines.delay

private const val TAG = "PasswordResetScreen"

@Suppress("ktlint:standard:function-naming")
/**
 * PasswordResetScreen allows users t reset passwords for existing accounts.
 * @param onNavigateToLogin Callback when the password reset email has been sent.
 */
@Composable
fun ResetPassword(onNavigateToLogin: () -> Unit = {}) {
    // Logging on screen entry
    LaunchedEffect(Unit) {
        Log.d(TAG, "Password Rest Screen Entered")
    }

    // Logging on screen exit
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Password Reset Screen exited")
        }
    }

    var email = remember { mutableStateOf("") }

    // Observe the state of the user verification
    val authViewModel: AuthViewModel = hiltViewModel()
    val resetState by authViewModel.resetState.collectAsState()

    BackgroundImage.Background {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Please enter the email used to create the account below. (You will receive the password reset email)",
                fontSize = 18.sp,
                color = AppColors.SSBlack,
                textAlign = TextAlign.Center,
            )

            Spacer(modifier = Modifier.height(12.dp))

            DefaultTextField(
                text = email.value,
                onTextChange = {
                    email.value = it
                    if (resetState is AuthViewModel.PasswordResetState.Error && it.isNotEmpty()) {
                        authViewModel.clearResetState()
                    }
                },
                modifier = Modifier.size(width = 300.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            SSSecondaryButton(
                text = "Send Password Reset Email",
                onClick = {
                    // Check email verification state every 3 seconds
                    authViewModel.resetPassword(email = email.value)
                    Log.d(TAG, "Password Reset Email has been sent, Navigating to Login Screen")
                },
                modifier = Modifier.size(width = 300.dp, height = 70.dp),
                enabled =
                    email.value.isNotEmpty() &&
                        resetState !is AuthViewModel.PasswordResetState.Loading,
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (resetState) {
                is AuthViewModel.PasswordResetState.Error -> {
                    val errorMessage = (resetState as AuthViewModel.PasswordResetState.Error).message
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp),
                    )
                    LaunchedEffect(errorMessage) {
                        Log.e(TAG, "Password reset error: $errorMessage")
                    }
                }
                is AuthViewModel.PasswordResetState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(20.dp))
                }
                is AuthViewModel.PasswordResetState.Success -> {
                    Text(
                        text = "Password reset email sent successfully!",
                        color = Color.Green,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(5.dp),
                    )
                    LaunchedEffect(Unit) {
                        Log.d(TAG, "Password Reset Email sent successfully, navigating to Login Screen")
                        delay(3000)
                        onNavigateToLogin()
                    }
                }
                else -> { /* Initial state - no action needed */ }
            }
        }
    }
}
