package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCasillasEditar(
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = Modifier
            .size(40.dp),
        onClick = onClick
    ) {
        Icon(
            Icons.Default.Add,
            contentDescription = "Agregar campo",
            tint = MaterialTheme.colorScheme.surface
        )
    }
}