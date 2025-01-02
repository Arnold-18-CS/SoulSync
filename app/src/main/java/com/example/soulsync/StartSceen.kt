package com.example.soulsync

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soulsync.ui.theme.BackgroundImage
import com.example.soulsync.ui.theme.SSPrimaryButton
import com.example.soulsync.ui.theme.SSSecondaryButton

@Suppress("ktlint:standard:function-naming")
/**
 * StartScreen allows users to navigate to Login or Register screens.
 * @param onNavigateToLogin Callback when the "Sign In" button is pressed.
 * @param onNavigateToRegister Callback when the "Sign Up" button is pressed.
 */

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {},
) {
    // Fetching the background and logo images
    painterResource(id = R.drawable.app_background)
    val logoImage = painterResource(id = R.drawable.app_logo)

    // Fetching app font and storing
    FontFamily(Font(R.font.emilys_candy, FontWeight.Normal))

    // Storing alpha and rotation for images by remember
    remember { 0.4f }

    BackgroundImage.Background {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Let's",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = MaterialTheme.typography.displayLarge.fontFamily,
            )

            // Load application logo at an angle
            Image(
                painter = logoImage,
                contentDescription = "SoulSync Logo",
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .size(width = 400.dp, height = 300.dp)
                        .padding(start = 20.dp),
            )

            // Use a lazy row to load buttons
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                content = {
                    item {
                        // Sign In Button
                        SSPrimaryButton(
                            text = "Sign In",
                            onClick = { onNavigateToLogin() },
                            modifier =
                                Modifier
                                    .size(width = 150.dp, height = 70.dp),
                        )
                    }
                    item {
                        // Sign Up Button
                        SSSecondaryButton(
                            text = "Sign Up",
                            onClick = { onNavigateToRegister() },
                            modifier =
                                Modifier
                                    .size(width = 150.dp, height = 70.dp),
                        )
                    }
                },
            )
        }
    }
}

@Suppress("ktlint:standard:function-naming")
// Preview for the start screen
@Preview(showBackground = true, showSystemUi = true, name = "Start Screen")
@Composable
fun StartScreenPreview() {
    StartScreen()
}
