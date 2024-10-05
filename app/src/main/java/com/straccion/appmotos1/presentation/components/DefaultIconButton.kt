package com.straccion.appmotos1.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier, // El modifier ahora es opcional
    onClick: () -> Unit,
    icon: ImageVector,
    contentDescription: String
){
    IconButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}