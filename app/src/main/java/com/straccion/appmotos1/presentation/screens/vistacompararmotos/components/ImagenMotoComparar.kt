package com.straccion.appmotos1.presentation.screens.vistacompararmotos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImagenMotoComparar(url: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "Moto image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit // Recorta la imagen para llenar el espacio
        )
    }
}