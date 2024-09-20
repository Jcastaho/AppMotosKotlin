package com.straccion.appmotos1.presentation.screens.vistabasededatos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.straccion.appmotos1.Pantallas
import com.straccion.appmotos1.presentation.components.DefaultOutlinedButton

@Composable
fun VistaBasedeDatos(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box

    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente los elementos
            modifier = Modifier.wrapContentSize()
        ) {
            item {
                DefaultOutlinedButton(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Agregar Registro",
                    onClick = { navController.navigate(Pantallas.AggRegistro.route) }
                )
                DefaultOutlinedButton(
                    modifier =Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp),
                    text = "Modificar Registro",
                    onClick = { navController.navigate(Pantallas.ModRegistro.route) }
                )
                DefaultOutlinedButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp),
                    text = "Eliminar/Ocultar Registro",
                    onClick = { navController.navigate(Pantallas.ElimRegistro.route) }
                )
            }
        }
    }
}