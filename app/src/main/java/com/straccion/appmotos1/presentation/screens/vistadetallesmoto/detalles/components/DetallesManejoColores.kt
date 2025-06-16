package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components

import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoViewModel

@Composable
@RequiresApi(35)
fun DetallesManejoColores(
    colorString: String,
    viewModel: DetallesMotoViewModel = hiltViewModel()
): Color {
    val normalizedColor = colorString.uppercase().replace(Regex("[^A-Z]"), "")
    val colorState = remember { mutableStateOf<Color>(Color.Gray) }

    val localColor = when {
        colorString.startsWith("#") -> {
            try {
                Color(android.graphics.Color.parseColor(colorString))
            } catch (e: IllegalArgumentException) {
                null
            }
        }

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
        else -> null
    }

    // Si se encontró un color local, actualiza el estado inmediatamente
    // Si se encontró un color local, actualiza el estado inmediatamente
    if (localColor != null) {
        colorState.value = localColor
    }
//    else {
//        // Si no se encontró, consulta a la IA
//        LaunchedEffect(normalizedColor) {
//            val colorFromViewModel = viewModel.sendPrompt(colorString).await()
//            colorFromViewModel?.let { hex ->
//                try {
//                    colorState.value = Color(android.graphics.Color.parseColor(hex))
//                } catch (e: IllegalArgumentException) {
//                    colorState.value = Color.Gray
//                }
//            }
//        }
//    }

    return colorState.value
}