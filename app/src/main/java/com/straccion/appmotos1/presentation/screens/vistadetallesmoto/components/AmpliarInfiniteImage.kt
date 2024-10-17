package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.straccion.appmotos1.domain.model.CategoriaMotos

@Composable
fun AmpliarInfiniteImage(
    imageUrl: String,
    imageIndex: Int,
    moto: CategoriaMotos,
    onDismiss: () -> Unit
) {
    val textoCaracteristicas = moto.caracteristicasTexto.toList().getOrNull(imageIndex)
    var imageLoadError by remember { mutableStateOf(false) }

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageLoadError) {
                Text(
                    text = "Error al cargar la imagen",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = "Imagen detallada",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    onError = {
                        imageLoadError = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = textoCaracteristicas ?: "",
                textAlign = TextAlign.Justify,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 46.dp, end = 16.dp)
                .size(48.dp)
                .clickable(onClick = onDismiss)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}