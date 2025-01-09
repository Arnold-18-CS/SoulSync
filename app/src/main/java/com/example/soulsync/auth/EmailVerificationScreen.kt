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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.ui.theme.AppColors
import com.example.soulsync.ui.theme.BackgroundImage
import com.example.soulsync.ui.theme.SSSecondaryButton
import kotlinx.coroutines.delay

private const val TAG = "EmailVerificationScreen"

@Suppress("ktlint:standard:function-naming")
/**
 * EmailVerificationScreen allows users to register using email.
 * @param onNavigateToLogin Callback when the email has been verified.
 * @param onResendEmail Callback when the user could not receive the verification email
 */
@Composable
fun VerifyEmail(
    onNavigateToLogin: () -> Unit = {},
    onResendEmail: () -> Unit = {},
) {
    // Logging on screen entry
    LaunchedEffect(Unit) {
        Log.d(TAG, "Email Verification Screen Entered")
    }

    // Logging on screen exit
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Email Verification Screen exited")
        }
    }

    // Observe the state of the user verification
    val authViewModel: AuthViewModel = hiltViewModel()
    val isEmailVerified by authViewModel.isEmailVerified.collectAsState()
    val registerState by authViewModel.registerState.collectAsState()

    // Check email verification state every 3 seconds
    LaunchedEffect(Unit) {
        while (!isEmailVerified) {
            delay(3000)
            authViewModel.checkEmailVerification()
        }
        if (isEmailVerified) {
            Log.d(TAG, "Email has been verified, Navigating to Login Screen")
            onNavigateToLogin()
        }
    }

    BackgroundImage.Background {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp),
        ) {
            Text(
                text = "Verification Email has been sent! Please check your inbox",
                fontSize = 18.sp,
                color = AppColors.SSBlack,
            )

            Spacer(modifier = Modifier.height(20.dp))

            SSSecondaryButton(
                text = "Resend Verification Email",
                onClick = {
                    authViewModel.resendVerificationEmail()
                },
                modifier = Modifier.size(width = 300.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (registerState is AuthViewModel.RegisterState.Error) {
                val errorMessage = (registerState as AuthViewModel.RegisterState.Error).message

                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(5.dp),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (registerState is AuthViewModel.RegisterState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(20.dp))
            }
        }
    }
}
