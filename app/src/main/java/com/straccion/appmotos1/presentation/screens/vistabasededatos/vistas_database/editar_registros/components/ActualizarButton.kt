package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.editar_registros.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.presentation.components.AlertDialogPregunta
import com.straccion.appmotos1.presentation.components.DefaultButton

@Composable
fun ActualizarButton(
    onConfirm: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    var showConfirmDialog by remember { mutableStateOf(false) }
    DefaultButton(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp)) // Botón con bordes redondeados
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
            .shadow(8.dp, RoundedCornerShape(28.dp)), // Sombra para efecto 3D
        text = "Actualizar",
        onClick = {
            focusManager.clearFocus()
            showConfirmDialog = true
        }
    )
    if (showConfirmDialog) {
        AlertDialogPregunta(
            onDismissRequest = { showConfirmDialog = false },
            onConfirmation = {
                onConfirm()
                showConfirmDialog = false
            },
            dialogTitle = "Confirmar actualización",
            dialogText = "¿Estás seguro de que quieres actualizar la ficha técnica?",
            icon = Icons.Filled.Info
        )
    }
}