package com.straccion.appmotos1.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.straccion.appmotos1.R


@Composable
fun DefaultAsyncImage(
    url: String
) {
    // Estado para verificar si la imagen principal se ha cargado
    val imageLoaded = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        // Box para superponer la imagen y el GIF
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (!imageLoaded.value) {
                ShimmerEffect()
            }

            // Imagen principal
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
                    .scale(1.07f),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .allowHardware(true)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(url)
                    .diskCacheKey(url)
                    .listener(
                        onSuccess = { _, _ ->
                            imageLoaded.value = true
                        }
                    )
                    .build(),
                contentScale = ContentScale.Fit,
                contentDescription = "Moto image"
            )
        }
    }
}
@Composable
fun ShimmerEffect() {
    val infiniteTransition = rememberInfiniteTransition()
    val gradientOffsetX by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    val gradientColors = listOf(
        Color.Gray.copy(alpha = 0.6f),
        Color.Gray.copy(alpha = 0.4f),
        Color.Gray.copy(alpha = 0.6f)
    )

    Canvas (
        modifier = Modifier.fillMaxSize()
    ) {
        val gradientBrush = Brush.linearGradient(
            colors = gradientColors,
            start = Offset(gradientOffsetX * size.width, 0f),
            end = Offset(gradientOffsetX * size.width + size.width, size.height)
        )

        drawRect(brush = gradientBrush)
    }
}