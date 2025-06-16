package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import android.widget.Toast
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistaestadistica.EstadisticaViewModel

@Composable
fun GetDatosEstadistica(
    paddingValues: PaddingValues,
    viewModel: EstadisticaViewModel = hiltViewModel()
) {
    val motosResponse by viewModel.motos.collectAsState()
    val context = LocalContext.current


    when (val response = motosResponse) {
        Response.Loading -> {
            DefaultProgressBar()
        }
        is Response.Success -> {
            VistaGraficos()
        }
        is Response.Failure -> {
            Toast.makeText(
                context,
                response.exception?.message ?: "Error desconocido",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}