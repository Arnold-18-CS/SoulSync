package com.example.soulsync

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.soulsync.navigation.NavigationGraph
import com.example.soulsync.ui.theme.SSNavigationBar
import com.example.soulsync.ui.theme.SoulSyncTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN)
        setContent {
            SoulSyncTheme {
                val navController = rememberNavController()
                var buttonsVisible by remember { mutableStateOf(true) }

                Scaffold(
                    bottomBar = {
                        if (buttonsVisible) {
                            SSNavigationBar(
                                navController = navController,
                                state = buttonsVisible,
                                modifier = Modifier,
                            )
                        }
                    },
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        NavigationGraph(navController = navController) { isVisible ->
                            buttonsVisible = isVisible
                        }
                    }
                }
            }
        }
    }
}
