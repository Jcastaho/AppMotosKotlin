package com.straccion.appmotos1.vistabasededatos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.straccion.appmotos1.Pantallas

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
                OutlinedButton(
                    onClick = { navController.navigate(Pantallas.AggRegistro.route) },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Text(
                        style = TextStyle(
                            fontSize = 22.sp
                        ),
                        text = "Agregar Registro"
                    )
                }
                OutlinedButton(
                    onClick = { navController.navigate(Pantallas.ModRegistro.route) },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        style = TextStyle(
                            fontSize = 22.sp
                        ),
                        text = "Modificar Registro"
                    )
                }
                OutlinedButton(
                    onClick = { navController.navigate(Pantallas.ElimRegistro.route) },
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 10.dp)
                ) {
                    Text(
                        style = TextStyle(
                            fontSize = 20.sp
                        ),
                        text = "Eliminar/Ocultar Registro"
                    )
                }
            }
        }
    }
}