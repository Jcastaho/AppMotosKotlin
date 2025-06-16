package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ImagenMotoEditar(
    imagenPrincipal: List<String>
) {
    val imageLoadError = remember { mutableStateOf(false) }
    val imageRequest = imagenPrincipal.firstOrNull()?.let {
        ImageRequest.Builder(LocalContext.current)
            .data(it)
            .crossfade(true)
            .build()
    }
    Spacer(modifier = Modifier.padding(top = 55.dp))
    if (imageLoadError.value || imagenPrincipal.isEmpty()) {
        Text(
            "Error al cargar la imagen",
            color = Color.Red, modifier = Modifier.padding(16.dp)
        )
    } else {
        AsyncImage(
            model = imageRequest,
            contentDescription = "Imagen detallada",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            onError = { imageLoadError.value = true }
        )
    }
}