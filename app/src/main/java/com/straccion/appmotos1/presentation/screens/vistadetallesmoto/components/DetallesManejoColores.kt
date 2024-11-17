package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun DetallesManejoColores(colorString: String): Color {
    val normalizedColor = colorString.uppercase().replace(Regex("[^A-Z]"), "")
    return when {
        colorString.startsWith("#") -> {
            try {
                Color(android.graphics.Color.parseColor(colorString))
            } catch (e: IllegalArgumentException) {
                Color.Gray // Color predeterminado si no se puede parsear
            }
        }

        else -> {
            when {
                normalizedColor.contains("NEGR") -> Color.Black
                normalizedColor.contains("BLANC") -> Color.White
                normalizedColor.contains("ROJ") -> Color.Red
                normalizedColor.contains("VERDE") -> Color.Green
                normalizedColor.contains("AZUL") -> Color.Blue
                normalizedColor.contains("AMARILL") -> Color.Yellow
                normalizedColor.contains("NARANJA") -> Color(0xFFFFA500)
                normalizedColor.contains("PURPURA") || normalizedColor.contains("MORADO") -> Color(0xFF800080)
                normalizedColor.contains("ROSA") -> Color(0xFFFFC0CB)
                normalizedColor.contains("MARRON") || normalizedColor.contains("CAFE") -> Color(0xFF8B4513)
                normalizedColor.contains("CYAN") || normalizedColor.contains("CIAN") -> Color.Cyan
                normalizedColor.contains("MAGENTA") || normalizedColor.contains("FUCSIA") -> Color.Magenta
                // Agrega más colores según sea necesario
                else -> Color.Gray // Color predeterminado si no se reconoce el nombre
            }
        }
    }
}