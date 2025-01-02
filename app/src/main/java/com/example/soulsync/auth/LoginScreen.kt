package com.example.soulsync.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.R
import com.example.soulsync.ui.theme.AppColors
import com.example.soulsync.ui.theme.EmailTextField
import com.example.soulsync.ui.theme.PasswordTextField
import com.example.soulsync.ui.theme.SSPrimaryButton

@Suppress("ktlint:standard:function-naming")
/**
 * LoginScreen allows users to navigate to login using email.
 * @param onNavigateToRegister Callback when the user does not have an account.
 * @param onNavigateToHome Callback when the login is successful.
 */
@Composable
fun LoginUser(
    onNavigateToRegister: () -> Unit = {},
    onNavigateToHome: () -> Unit = {},
) {
    // Fetching the background image and storing its alpha
    val bgImage = painterResource(id = R.drawable.app_background)
    val alpha = remember { 0.4f }

    // Initializing variables for user input
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    // Observe the login state using the authViewModel
    val authViewModel: AuthViewModel = hiltViewModel()
    val loginState by authViewModel.loginState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier =
            Modifier
                .fillMaxSize()
                .paint(
                    painter = bgImage,
                    contentScale = ContentScale.FillBounds,
                    alpha = alpha,
                ),
    ) {
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
                modifier = Modifier.clickable { onNavigateToRegister() },
            )

            Spacer(modifier = Modifier.padding(15.dp))

            // Email text field
            EmailTextField(
                text = email.value,
                onTextChange = { email.value = it },
                modifier = Modifier.size(width = 350.dp, height = 70.dp),
            )

            Spacer(modifier = Modifier.padding(12.dp))

            // Password entry field
            PasswordTextField(
                password = password.value,
                onPasswordChange = { password.value = it },
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
                modifier = Modifier.clickable { /*TODO*/ },
            )

            Spacer(modifier = Modifier.padding(20.dp))

            // Login button
            SSPrimaryButton(
                text = "Sign In",
                onClick = {
                    if (email.value.isNotEmpty() && password.value.isNotEmpty()) {
                        authViewModel.loginUser(email.value, password.value)
                    } else {
                        authViewModel.setLoginError("!! Please fill in both fields !!")
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
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
                }
                // Show green success message then reroute
                is AuthViewModel.LoginState.Success -> {
                    Text(
                        text = "Login successful! Redirecting...",
                        color = Color.Green,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                    )
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
