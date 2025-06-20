package com.straccion.appmotos1.presentation.screens.vistacompararmotos.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size

@Composable
fun ImagenMotoComparar(url: String) {
    val imageLoaded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(
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
                    .build()
            ),
            contentDescription = "Moto image",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Fit // Recorta la imagen para llenar el espacio
        )
    }
}