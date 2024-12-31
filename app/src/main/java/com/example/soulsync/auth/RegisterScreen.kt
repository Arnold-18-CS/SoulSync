package com.example.soulsync.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.soulsync.R
import com.example.soulsync.ui.theme.AppColors

/**
 * RegisterScreen allows users to register using email.
 * @param onNavigateToLogin Callback if the user already has an account.
 */
@Composable
fun RegisterUser(
    onNavigateToLogin: () -> Unit = {},
){
    // Fetching the background image and storing its alpha
    val bgImage = painterResource(id = R.drawable.wallpaper_in_purple_aesthetic)
    val alpha = remember { 0.4f }


    // Creating variables for user input
    var email = rememberSaveable { mutableStateOf("") }
    var password = rememberSaveable { mutableStateOf("") }
    var confirmPassword = rememberSaveable { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(true) }

    // Observe the registration state using the authViewModel
    val authViewModel: AuthViewModel = hiltViewModel()
    val registerState by authViewModel.registerState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = bgImage,
                contentScale = ContentScale.FillBounds,
                alpha = alpha
            )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header for screen
            Text(
                text = "Sign Up",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                modifier = Modifier.padding(15.dp)
                )

            Spacer(modifier = Modifier.padding(8.dp))

            // Input field for email
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("Email") },
                placeholder = { Text("Enter your email") },
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Email, contentDescription = "Email")
                              },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear Email",
                        modifier = Modifier.clickable { email.value = "" }
                    )
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            // Input field for user password
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                placeholder = { Text("Enter your password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Password, contentDescription = "Password")
                },
                trailingIcon = {
                    if(showPassword){
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.clickable { showPassword = !showPassword }
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.clickable { showPassword = !showPassword }
                        )
                    }
                               },
                singleLine = true,
                visualTransformation = if(showPassword){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp)
            )

            Spacer(modifier = Modifier.padding(12.dp))

            // Input field for confirm password
            OutlinedTextField(
                value = confirmPassword.value,
                onValueChange = { confirmPassword.value = it },
                label = { Text("Confirm Password") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Confirm Password")
                },
                trailingIcon = {
                    if(showPassword){
                        Icon(
                            imageVector = Icons.Filled.Visibility,
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.clickable { showPassword = !showPassword }
                        )
                    }else{
                        Icon(
                            imageVector = Icons.Filled.VisibilityOff,
                            contentDescription = "Toggle Password Visibility",
                            modifier = Modifier.clickable { showPassword = !showPassword }
                        )
                    }
                },
                singleLine = true,
                visualTransformation = if(showPassword){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },
                modifier = Modifier.size(width = 350.dp, height = 70.dp)
            )

            Spacer(modifier = Modifier.padding(10.dp))

            // Clickable link to reroute to login page if user has an account
            Text(
                text = "Already have an account? Click here",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.SSPrimaryPurple,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onNavigateToLogin() }
            )

            Spacer(modifier = Modifier.padding(20.dp))

            // Register button
            ElevatedButton(
                onClick = {
                    if(email.value.isNotEmpty() && password.value.isNotEmpty() && confirmPassword.value.isNotEmpty()){
                        if(password.value == confirmPassword.value){
                            authViewModel.registerUser(email.value, password.value)
                        }else{
                            authViewModel.setRegisterState("Passwords do not match")
                        }
                    }
                    else{
                        authViewModel.setRegisterState("!! Please fill in all fields !!")
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color(AppColors.SSPrimaryPurple.value),
                    contentColor = Color(AppColors.SSWhite.value)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 13.dp,
                    pressedElevation = 3.dp
                ),
                modifier = Modifier.size(width = 300.dp, height = 70.dp)
            ){
                Text(
                    text = "Sign Up",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))

            // Display the registration state using the authViewModel
            when (registerState){
                is AuthViewModel.RegisterState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
                // Runs on Failure to Register
                is AuthViewModel.RegisterState.Error -> {
                    val errorMessage = (registerState as AuthViewModel.RegisterState.Error).message
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    LaunchedEffect(Unit) {
                        kotlinx.coroutines.delay(2000)
                        authViewModel.setRegisterState(message = "")
                    }
                }
                // Run on Successful Registration
                is AuthViewModel.RegisterState.Success -> {
                    Text(
                        text = "Registration successful! Redirecting...",
                        color = Color.Green,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    onNavigateToLogin()
                }

                else -> Unit // Initial State
            }
        }
    }
}

// Preview Function for register screen
@Preview(showBackground = true, showSystemUi = true, name="Register Screen")
@Composable
fun RegisterUserPreview(){
    RegisterUser()
}