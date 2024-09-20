package com.straccion.appmotos1.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun DefaultButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    colors: ButtonColors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Agregamos un parámetro para los colores
    enabled: Boolean = true,
    fontSize: Int = 14,
    contentPadding: PaddingValues? = PaddingValues()
){
    Button(
        modifier = modifier,
        onClick = { onClick() },
        colors = colors,
        enabled = enabled,
        contentPadding = contentPadding?: PaddingValues(),
    ) {
        Text(
            text,
            fontSize = fontSize.sp
        )

    }
}