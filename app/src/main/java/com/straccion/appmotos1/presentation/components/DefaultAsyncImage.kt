package com.straccion.appmotos1.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage


@Composable
fun DefaultAsyncImage(
    url: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Esto hará que el contenedor sea cuadrado
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .scale(1.07f),
            contentScale = ContentScale.Fit, // Recorta la imagen para llenar el espacio
            model = url,
            contentDescription = "Moto image"
        )
    }
}