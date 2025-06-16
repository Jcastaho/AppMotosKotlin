package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components.ObtenerMotoEditar
import com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components.UpdateFichaTec

@Composable
fun EditarRegistroScreen(
    viewModel: EditarRegistrosViewModel = hiltViewModel()
) {
    val motos by viewModel.moto.collectAsState()

    viewModel.setFichaTecnica(motos.fichaTecnica)
    Scaffold(
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                ObtenerMotoEditar(motos)
            }
        }
    )
    UpdateFichaTec()
}