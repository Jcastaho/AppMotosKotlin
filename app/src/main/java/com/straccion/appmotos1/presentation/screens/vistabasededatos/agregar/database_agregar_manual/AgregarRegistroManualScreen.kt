package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController


@Composable
fun AgregarRegistroManualScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {

        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding) ){
                Column {
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                    Text(text = "Prueba")
                }

            }
        }
    )

}