package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar.components.VentanasAggRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_agregar.components.VistaAggRegistro


@Composable
fun AggRegistroScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                VistaAggRegistro()
            }
        }
    )
    VentanasAggRegistro(navHostController)
}