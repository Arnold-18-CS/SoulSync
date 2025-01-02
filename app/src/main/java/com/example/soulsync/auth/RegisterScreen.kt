package com.example.soulsync.auth

import android.util.Log
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.saveable.rememberSaveable
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

private const val TAG = "RegisterScreen"

@Suppress("ktlint:standard:function-naming")
/**
 * RegisterScreen allows users to register using email.
 * @param onNavigateToLogin Callback if the user already has an account.
 */
@Composable
fun RegisterUser(onNavigateToLogin: () -> Unit = {}) {
    // Logging on screen entry
    LaunchedEffect(Unit) {
        Log.d(TAG, "Register Screen Entered")
    }

    // Logging on screen exit
    DisposableEffect(Unit) {
        onDispose {
            Log.d(TAG, "Register screen exited")
        }
    }

    // Creating variables for user input
    var email = rememberSaveable { mutableStateOf("") }
    var password = rememberSaveable { mutableStateOf("") }
    var confirmPassword = rememberSaveable { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(true) }

    // Observe the registration state using the authViewModel
    val authViewModel: AuthViewModel = hiltViewModel()
    val registerState by authViewModel.registerState.collectAsState()

    BackgroundImage.Background {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Header for screen
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            )

            Spacer(modifier = Modifier.height(10.dp))

            EmailTextField(
                text = email.value,
                onTextChange = {
                    email.value = it
                    Log.v(TAG, "Email updated")
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.height(10.dp))

            PasswordTextField(
                password = password.value,
                onPasswordChange = {
                    password.value = it
                    Log.v(TAG, "Password updated")
                },
                showPassword = showPassword,
                onTogglePasswordVisibility = {
                    showPassword = !showPassword
                    Log.v(TAG, "Password visibility updated")
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            PasswordTextField(
                password = confirmPassword.value,
                onPasswordChange = {
                    confirmPassword.value = it
                    Log.v(TAG, "Confirm Password updated")
                },
                showPassword = showPassword,
                onTogglePasswordVisibility = {
                    showPassword = !showPassword
                    Log.v(TAG, "Confirm Password visibility updated")
                },
                label = "Confirm Password",
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Already have an account? Click here",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.SSPrimaryPurple,
                textDecoration = TextDecoration.Underline,
                modifier =
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = LocalIndication.current,
                    ) {
                        Log.v(TAG, "Navigate to Login, from Register")
                        onNavigateToLogin()
                    },
            )

            Spacer(modifier = Modifier.height(20.dp))

            SSPrimaryButton(
                text = "Sign Up",
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()) {
                        Log.v(TAG, "Register button pressed, all values filled")
                        if (password.value == confirmPassword.value) {
                            Log.v(TAG, "Passwords match, login in process started")
                            authViewModel.registerUser(email.value, password.value)
                            Log.i(TAG, "Login in process successful")
                        } else {
                            authViewModel.setRegisterState(message = "Passwords do not match")
                            Log.w(TAG, "Displaying validation error \"Passwords do not match\"")
                        }
                    } else {
                        authViewModel.setRegisterState(message = "Please fill in all fields")
                        Log.w(TAG, "Displaying validation error \"Please fill in all fields\"")
                    }
                },
                isLoading = registerState is AuthViewModel.RegisterState.Loading,
                enabled = email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty(),
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            // Display the registration state using the authViewModel
            when (registerState) {
                is AuthViewModel.RegisterState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                // Runs on Failure to Register
                is AuthViewModel.RegisterState.Error -> {
                    val errorMessage = (registerState as AuthViewModel.RegisterState.Error).message
                    Log.e(TAG, "Registration failed: $errorMessage")
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        authViewModel.setRegisterState(message = "")
                    }
                }
                // Run on Successful Registration
                is AuthViewModel.RegisterState.Success -> {
                    Log.i(TAG, "Registration successful")
                    Text(
                        text = "Registration successful! Redirecting...",
                        color = Color.Green,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                    Log.d(TAG, "Navigating to Login, from Register")
                    onNavigateToLogin()
                }

                else -> Unit // Initial State
            }
        }
    }
}

@Suppress("ktlint:standard:function-naming")
// Preview Function for register screen
@Preview(showBackground = true, showSystemUi = true, name = "Register Screen")
@Composable
fun RegisterUserPreview() {
    RegisterUser()
}
