package com.example.soulsync

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soulsync.ui.theme.AppColors

/**
 * StartScreen allows users to navigate to Login or Register screens.
 * @param onNavigateToLogin Callback when the "Sign In" button is pressed.
 * @param onNavigateToRegister Callback when the "Sign Up" button is pressed.
 */

@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit = remember{ {} },
    onNavigateToRegister: () -> Unit = remember { {} }
){
    // Fetching the background and logo images
    val bgImage = painterResource(id = R.drawable.wallpaper_in_purple_aesthetic)
    val logoImage = painterResource(id = R.drawable.soul_sync_logo)

    // Fetching app font and storing
//    val appFont = FontFamily(Font(R.font.emilys_candy, FontWeight.Normal))

    // Storing alpha and rotation for images by remember
    val alpha = remember { 0.4f }
    val rotationAngle = remember { 2f }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = bgImage,
                contentScale = ContentScale.FillBounds,
                alpha = alpha
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Let's",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
//                fontFamily = MaterialTheme.typography.displayLarge.fontFamily
            )

            // Load application logo at an angle
            Image(
                painter = logoImage,
                contentDescription = "SoulSync Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 400.dp, height = 300.dp)
                    .padding(start = 20.dp)
                    .graphicsLayer { this.rotationZ = rotationAngle }
            )

            // Use a lazy row to load buttons
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                content = {
                    item{
                        // Sign In Button
                        ElevatedButton(
                            onClick = { onNavigateToLogin() },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color(AppColors.SSPrimaryPurple.value),
                                contentColor = Color(AppColors.SSWhite.value)
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 13.dp,
                                pressedElevation = 3.dp
                            ),
                            modifier = Modifier
                                .size(width = 150.dp, height = 70.dp)
                        ){
                            Text(
                                text = "Sign In",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    item{
                        // Sign Up Button
                        ElevatedButton(
                            onClick = { onNavigateToRegister() },
                            colors = ButtonDefaults.elevatedButtonColors(
                                containerColor = Color(AppColors.SSSecondaryPurple.value),
                                contentColor = Color(AppColors.SSPrimaryPurple.value),
                            ),
                            elevation = ButtonDefaults.elevatedButtonElevation(
                                defaultElevation = 13.dp,
                                pressedElevation = 3.dp,
                            ),
                            modifier = Modifier
                                .size(width = 150.dp, height = 70.dp)
                        ){
                            Text(
                                text = "Sign Up",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            )
        }
    }
}

// Preview for the start screen
@Preview(showBackground = true, showSystemUi = true, name="Start Screen")
@Composable
fun StartScreenPreview(){
    StartScreen()
}