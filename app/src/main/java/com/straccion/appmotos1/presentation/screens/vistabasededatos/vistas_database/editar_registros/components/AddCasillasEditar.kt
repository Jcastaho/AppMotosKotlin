package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.editar_registros.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddCasillasEditar(
    onClick: () -> Unit
) {
    FloatingActionButton(
    onClick = onClick,
    modifier = Modifier.size(40.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Agregar campo")
    }
}