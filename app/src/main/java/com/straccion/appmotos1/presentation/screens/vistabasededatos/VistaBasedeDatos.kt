package com.straccion.appmotos1.presentation.screens.vistabasededatos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.navigation.Pantallas
import com.straccion.appmotos1.presentation.components.DefaultOutlinedButton
import com.straccion.appmotos1.presentation.navigation.DrawerScreen

@Composable
fun VistaBasedeDatos(navHostController: NavHostController) {
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
                    onClick = { navHostController.navigate(DrawerScreen.Base_Datos_Vista.AgregarRegistro.route) }
                )
                DefaultOutlinedButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp),
                    text = "Modificar Registro",
                    onClick = { navHostController.navigate(DrawerScreen.Base_Datos_Vista.ModificarRegistro.route) }
                )
                DefaultOutlinedButton(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp),
                    text = "Eliminar/Ocultar Registro",
                    onClick = { navHostController.navigate(DrawerScreen.Base_Datos_Vista.EliminarRegistro.route) }
                )
            }
        }
    }
}