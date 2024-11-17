package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar.components.VentanasAggRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar.components.VistaAggRegistro


@Composable
fun AggRegistroScreen(navHostController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {

            },
            content = { paddingValues ->
                // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
                Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                    VistaAggRegistro()
                }
            }
        )
        VentanasAggRegistro(navHostController)
    }
}