package com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.components.AlertDialogPregunta
import com.straccion.appmotos1.presentation.components.DefaultIconButton
import com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.ElimRegistroViewModel


@Composable
fun CardMotosElim(
    motos: CategoriaMotos,
    viewModel: ElimRegistroViewModel = hiltViewModel()
) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var ocultarMoto by remember { mutableStateOf(false) }
    var textoDialogMostrar = ""
    var tituloDialogMostrar = ""
    var esOcultar by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    if (motos.visible) {
                        Color.Transparent
                    } else {
                        Color.Gray
                    }
                )
                .padding(8.dp), // Añade padding para separar el contenido de los bordes del Card
            verticalAlignment = Alignment.CenterVertically // Alinea vert
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    contentScale = ContentScale.Fit, // Recorta la imagen para llenar el espacio
                    model = motos.imagenesPrincipales.get(0),
                    contentDescription = "Moto image"
                )
            }
            Spacer(modifier = Modifier.padding(start = 5.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = motos.id,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .size(100.dp), // Ajusta el tamaño del espacio para el botón según lo necesites
                contentAlignment = Alignment.Center // Centra el contenido dentro del Box
            ) {
                Row {
                    if (motos.visible) {
                        DefaultIconButton(
                            onClick = {
                                esOcultar = true
                                tituloDialogMostrar = "Ocultar Moto"
                                textoDialogMostrar = "¿Esta seguro que desea ocultar esta moto?"
                                ocultarMoto = false
                                showConfirmDialog = true
                            },
                            icon = Icons.Default.HideImage,
                            contentDescription = "Ocultar"
                        )
                    } else {
                        DefaultIconButton(
                            onClick = {
                                esOcultar = true
                                ocultarMoto = true
                                tituloDialogMostrar = "Mostrar Moto"
                                textoDialogMostrar =
                                    "¿Esta seguro que desea volver a mostrar esta moto?"
                                showConfirmDialog = true
                            },
                            icon = Icons.Default.RemoveRedEye,
                            contentDescription = "Ocultar"
                        )
                    }
                    DefaultIconButton(
                        onClick = {

                            esOcultar = false
                            tituloDialogMostrar = "Eliminar Moto"
                            textoDialogMostrar =
                                "¿Esta seguro que desea eliminar definitivamente esta moto?"
                            showConfirmDialog = true
                        },
                        icon = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                    if (showConfirmDialog) {
                        AlertDialogPregunta(
                            onDismissRequest = { showConfirmDialog = false },
                            onConfirmation = {
                                if (esOcultar) {
                                    viewModel.ocultarMoto(ocultarMoto, motos.id)
                                } else {
                                    viewModel.eliminarMoto(motos.id)
                                }
                                showConfirmDialog = false
                            },
                            dialogTitle = tituloDialogMostrar,
                            dialogText = textoDialogMostrar,
                            icon = Icons.Filled.Info
                        )
                    }
                }
            }
        }
    }
}