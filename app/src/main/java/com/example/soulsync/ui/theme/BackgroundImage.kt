package com.example.soulsync.ui.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.soulsync.R

object BackgroundImage {
    private var cachedPainter: Painter? = null

    @Suppress("ktlint:standard:function-naming")
    @Composable
    fun Background(
        alpha: Float = 0.4f,
        contentAlignment: Alignment = Alignment.Center,
        content: @Composable () -> Unit,
    ) {
        val painter =
            cachedPainter ?: painterResource(
                id = R.drawable.app_background,
            ).also {
                cachedPainter = it
            }

        Box(
            contentAlignment = contentAlignment,
            modifier =
                Modifier
                    .fillMaxSize()
                    .paint(
                        painter = painter,
                        alpha = alpha,
                        contentScale = ContentScale.FillBounds,
                    ),
        ) {
            content()
        }
    }
}
