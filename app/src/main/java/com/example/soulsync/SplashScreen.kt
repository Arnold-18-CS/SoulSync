package com.example.soulsync

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.soulsync.navigation.AppDestinations
import com.example.soulsync.ui.theme.BackgroundImage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Suppress("ktlint:standard:function-naming")
@Composable
fun SplashScreen(navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    LaunchedEffect(Unit) {
        if (currentUser != null) {
            navController.navigate(AppDestinations.HomeScreen.route) {
                popUpTo(AppDestinations.MainSplashScreen.route) { inclusive = true }
            }
        } else {
            navController.navigate(AppDestinations.StartScreen.route) {
                popUpTo(AppDestinations.MainSplashScreen.route) { inclusive = true }
            }
        }
    }

    BackgroundImage.Background {
        LaunchedEffect(Unit) {
            delay(3000)
        }
        CircularProgressIndicator(modifier = Modifier.padding(21.dp))
    }
}
