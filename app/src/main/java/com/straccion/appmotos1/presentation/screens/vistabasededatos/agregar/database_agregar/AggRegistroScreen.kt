package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.components.VentanasAggRegistro
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.components.VistaAggRegistro


@Composable
fun AggRegistroScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding) ){
                VistaAggRegistro(navHostController)
            }
        }
    )
    VentanasAggRegistro(navHostController)
}