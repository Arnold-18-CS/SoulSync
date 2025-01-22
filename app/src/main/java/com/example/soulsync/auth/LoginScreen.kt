package com.example.soulsync.auth

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.ui.theme.AppColors
import com.example.soulsync.ui.theme.BackgroundImage
import com.example.soulsync.ui.theme.EmailTextField
import com.example.soulsync.ui.theme.PasswordTextField
import com.example.soulsync.ui.theme.SSPrimaryButton

private const val TAG = "LoginScreen"

@Suppress("ktlint:standard:function-naming")
/**
 * LoginScreen allows users to navigate to login using email.
 * @param onNavigateToRegister Callback when the user does not have an account.
 * @param onNavigateToHome Callback when the login is successful.
 */
@Composable
fun LoginUser(
    onNavigateToRegister: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
) {
    // Logging on screen entry
    LaunchedEffect(Unit) {
        Log.d(TAG, "Login Screen Entered")
    }

    // Logging on screen exit
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Login screen exited")
        }
    }

    // Initializing variables for user input
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Observe the login state using the authViewModel
    val authViewModel: AuthViewModel = hiltViewModel()
    val loginState by authViewModel.loginState.collectAsState()

    BackgroundImage.Background {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header text for the page
            Text(
                text = "Sign In",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            )

            Spacer(modifier = Modifier.padding(10.dp))

            // Clickable link to reroute to register page
            Text(
                text = "Don't have an account yet? Click here",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.SSPrimaryPurple,
                textDecoration = TextDecoration.Underline,
                modifier =
                    Modifier.clickable {
                        Log.d(TAG, "Navigate to Register, from Login")
                        onNavigateToRegister()
                    },
            )

            Spacer(modifier = Modifier.padding(15.dp))

            // Email text field
            EmailTextField(
                text = email.value,
                onTextChange = {
                    email.value = it
                    Log.v(TAG, "Email updated")
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.padding(12.dp))

            // Password entry field
            PasswordTextField(
                password = password.value,
                onPasswordChange = {
                    password.value = it
                    Log.v(TAG, "Password updated")
                },
                showPassword = showPassword,
                onTogglePasswordVisibility = { showPassword = !showPassword },
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = "Forgot Password",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.SSPrimaryPurple,
                textDecoration = TextDecoration.Underline,
                modifier =
                    Modifier.clickable {
                        Log.d(TAG, "Navigate to Reset Password, from Login")
                        onNavigateToResetPassword()
                    },
            )

            Spacer(modifier = Modifier.padding(20.dp))

            // Login button
            SSPrimaryButton(
                text = "Sign In",
                onClick = {
                    Log.v(TAG, "Login button pressed")
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        Log.v(TAG, "Login in process started")
                        authViewModel.loginUser(email.value, password.value)
                        Log.i(TAG, "Login in process successful")
                    } else {
                        authViewModel.setLoginError("!! Please fill in both fields !!")
                        Log.w(TAG, "Displaying validation error \"Please fill in both fields\"")
                    }
                },
                isLoading = loginState is AuthViewModel.LoginState.Loading,
                enabled = email.value.isNotEmpty() && password.value.isNotEmpty(),
            )

            Spacer(modifier = Modifier.padding(10.dp))

            // Handling different login states on UI
            when (loginState) {
                // Show progress indicator if loading
                is AuthViewModel.LoginState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                // Show red error message if login fails
                is AuthViewModel.LoginState.Error -> {
                    val errorMessage = (loginState as AuthViewModel.LoginState.Error).message
                    Log.e(TAG, "Login failed: $errorMessage")
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
                // Show green success message then reroute
                is AuthViewModel.LoginState.Success -> {
                    Log.i(TAG, "Login successful")
                    Text(
                        text = "Login successful! Redirecting...",
                        color = Color.Green,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    Log.d(TAG, "Navigating to Home, from Login")
                    onNavigateToHome()
                }
                else -> Unit
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
// Preview Function for login screen
@Preview(showBackground = true, showSystemUi = true, name = "Login Screen")
@Composable
fun LoginUserPreview() {
    LoginUser()
}
