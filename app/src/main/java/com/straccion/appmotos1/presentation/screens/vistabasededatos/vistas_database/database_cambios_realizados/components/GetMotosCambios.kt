package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados.components

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados.CambiosRealizadosViewModel

@Composable
fun GetMotosCambios(
    viewModel: CambiosRealizadosViewModel = hiltViewModel()
) {
    val motosResponse by viewModel.datosCambiados.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    if (isLoading) {
        DefaultProgressBar()
    } else if (motosResponse.isEmpty()) {
        // Mostrar mensaje de no hay datos
        Text("No hay datos disponibles")
    } else {
        VistaCambios(motosResponse)
    }
}