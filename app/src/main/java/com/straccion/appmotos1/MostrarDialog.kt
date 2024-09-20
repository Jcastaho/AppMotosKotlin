package com.straccion.appmotos1

import android.graphics.drawable.Icon
import android.os.Build.VERSION.SDK_INT
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
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
    gifResourceId: Int
) {
    AlertDialog(
        icon = {
            GifImage(
                gifResourceId = gifResourceId,
                modifier = Modifier.size(48.dp)
            )
        },
        title = {
            Text(color = MaterialTheme.colorScheme.primary,
                text = dialogTitle
            )
        },
        text = {
            Text(
                color = MaterialTheme.colorScheme.primary,
                text = dialogText
            )
        },
        onDismissRequest = {
            onDismissRequest
        },
        confirmButton = {
            Button(
                onClick = onConfirmation,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp)) // Botón con bordes redondeados
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                        )
                    ),
                ) {
                Text(text = "Confirmar")
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
            Button(
                onClick = onConfirmation,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .clip(RoundedCornerShape(22.dp)) // Botón con bordes redondeados
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                        )
                    ),
            ) {
                Text(
                    text = "Confirmar"
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onDismissRequest,
                modifier = Modifier.clip(RoundedCornerShape(22.dp)) // Botón con bordes redondeados
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    text = "Cancelar"
                )
            }
        }
    )
}
