package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_cambios_realizados

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_cambios_realizados.components.GetMotosCambios

@Composable
fun CambiosRealizadosScreen(

) {
    Scaffold(
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                GetMotosCambios()
            }
        }
    )
}