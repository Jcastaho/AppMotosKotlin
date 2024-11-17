package com.straccion.appmotos1.presentation.components

import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest


@Composable
fun AlertDialogExitoso(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    gifResourceId: Int,
    mostrarBoton: Boolean = true,
    mostrarBotonAdd: Boolean = false,
    mostrarDatos: () -> Unit = {},
) {
    AlertDialog(
        icon = {
            GifImage(
                gifResourceId = gifResourceId,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = dialogTitle
            )
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = dialogText
                )
            }

        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            if (mostrarBoton) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center  // Esto centra el botón
                ) {
                    if (mostrarBotonAdd){
                        DefaultButton(
                            modifier = Modifier
                                .clip(RoundedCornerShape(22.dp))
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                                    )
                                ),
                            text = "Mostar Cambios",
                            contentPadding = PaddingValues(horizontal = 25.dp, vertical = 8.dp),
                            onClick = { mostrarDatos() }
                        )
                        Spacer(modifier = Modifier.padding(2.dp))
                    }
                    DefaultButton(
                        modifier = Modifier
                            .clip(RoundedCornerShape(22.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                                )
                            ),
                        text = "Confirmar",
                        contentPadding = PaddingValues(horizontal = 25.dp, vertical = 7.dp),
                        onClick = { onConfirmation() }
                    )
                }
            }
        }
    )
}

@Composable
fun GifImage(
    @DrawableRes gifResourceId: Int,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Image(
        painter = rememberAsyncImagePainter(
            ImageRequest.Builder(context).data(data = gifResourceId).build(),
            imageLoader = imageLoader
        ),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
fun AlertDialogPregunta(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector
) {
    AlertDialog(
        shape = RoundedCornerShape(16.dp),
        icon = {
            Icon(
                icon,
                contentDescription = "Icono de diálogo",
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        },
        onDismissRequest = onDismissRequest,
        confirmButton = {
            DefaultButton(
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp)) // Botón con bordes redondeados
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                        )
                    ),
                text = "Confirmar",
                contentPadding = PaddingValues(horizontal = 25.dp, vertical = 8.dp),
                onClick = { onConfirmation() }
            )
        },
        dismissButton = {
            DefaultOutlinedButton(
                modifier = Modifier.clip(RoundedCornerShape(22.dp)), // Botón con bordes redondeados
                text = "Cancelar",
                onClick = onDismissRequest
            )
        }
    )
}
