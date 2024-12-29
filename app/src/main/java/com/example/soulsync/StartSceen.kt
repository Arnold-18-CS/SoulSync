package com.example.soulsync

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true, showSystemUi = true, name="Start Screen")
@Composable
fun StartScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToRegister: () -> Unit = {}
){

    val bgImage = painterResource(id = R.drawable.wallpaper_in_purple_aesthetic)

    val appFont = FontFamily(Font(R.font.emilys_candy, FontWeight.Normal))
    val image = painterResource(id = R.drawable.soul_sync_logo)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = bgImage,
                contentScale = ContentScale.FillBounds,
                alpha = 0.4f
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = "Let's",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = appFont
            )

            Image(
                painter = image,
                contentDescription = "SoulSync Logo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 400.dp, height = 300.dp)
                    .clip(shape = CircleShape)
                    .padding(start = 20.dp)
                    .graphicsLayer { this.rotationZ = 2f }
            )

            Row{
                ElevatedButton(
                    onClick = { onNavigateToLogin() },
                    colors = ButtonColors(
                        containerColor = Color(0xFF9279C4),
                        contentColor = Color.White,
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 13.dp,
                        pressedElevation = 3.dp,
                        disabledElevation = 0.dp
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

                Spacer(modifier = Modifier.size(width = 20.dp, height = 0.dp))

                ElevatedButton(
                    onClick = { onNavigateToRegister() },
                    colors = ButtonColors(
                        containerColor = Color(0xFFD7CCE5),
                        contentColor = Color(0xFF9279C4),
                        disabledContainerColor = Color.LightGray,
                        disabledContentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.elevatedButtonElevation(
                        defaultElevation = 13.dp,
                        pressedElevation = 3.dp,
                        disabledElevation = 0.dp
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
    }
}




