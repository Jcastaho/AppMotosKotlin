package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.R
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.AlertDialogExitoso
import com.straccion.appmotos1.presentation.components.AlertDialogPregunta
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavAgregarRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.AggRegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanasAggRegistro(
    navHostController: NavHostController,
    viewModel: AggRegistroViewModel = hiltViewModel()
) {
    val fabricanteText by viewModel.fabricanteText.collectAsState()
    val marcaText by viewModel.marcaText.collectAsState()
    val categoriaText by viewModel.categoriaText.collectAsState()
    val mostrarProgresBar by viewModel.mostrarProgresBar.collectAsState()
    val dialogResultWebScraping by viewModel.dialogResultWebScraping.collectAsState()
    var dialogResultWebScraping2 by remember { mutableStateOf(false) }

    // val motosResponse by viewModel.motosResponse.collectAsState()
    val listaFabricantes by viewModel.listaFabricantes.collectAsState()
    val listaMarcas by viewModel.listaMarcas.collectAsState()
    val listaCategorias by viewModel.listaCategorias.collectAsState()

    val motosUpdateState by viewModel.motosUpdateState.collectAsState()

    if (fabricanteText.first) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.ocultarFabricante("") }
        ) {
            LazyColumn {
                items(listaFabricantes.distinct()) { fabricante ->
                    ListItem(
                        headlineContent = { Text(fabricante.toString()) },
                        modifier = Modifier.clickable {
                            viewModel.ocultarFabricante(fabricante)
                        }
                    )
                }
            }

        }
    }

    if (marcaText.first) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.ocultarMarca("") }
        ) {
            LazyColumn() {
                items(listaMarcas.distinct()) { marca ->
                    ListItem(
                        headlineContent = { Text(marca.toString()) },
                        modifier = Modifier
                            .clickable {
                                viewModel.ocultarMarca(marca)
                            }
                    )
                }
            }
        }
    }
    if (categoriaText.first) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.ocultarCategoria("") },
        ) {
            LazyColumn {
                items(listaCategorias.distinct()) { categoria ->
                    ListItem(
                        headlineContent = { Text(categoria.toString()) },
                        modifier = Modifier.clickable {
                            viewModel.ocultarCategoria(categoria)
                        }
                    )
                }
            }
        }
    }

    if (dialogResultWebScraping) {
        AlertDialogPregunta(
            onDismissRequest = { viewModel.hideDialogWebScraping() },
            onConfirmation = {
                viewModel.hideDialogWebScraping()
                viewModel.mostrarProgress()
                viewModel.actualizarMotos()
                dialogResultWebScraping2 = true
            },
            dialogTitle = "Web Scraping",
            dialogText = "¿Desea iniciar el Web Scraping?",
            icon = Icons.Filled.Info
        )
    }

    // Progress Bar
    if (motosUpdateState is Response.Loading && mostrarProgresBar) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    }

    when (motosUpdateState) {
        is Response.Loading -> {
            if (mostrarProgresBar) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(enabled = false) {}, // Evita interacciones debajo del indicador
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color.Black)
                }
            }
        }

        is Response.Success -> {
            // Muestra un mensaje de éxito
            viewModel.ocultarProgress()
            if (dialogResultWebScraping2) {
                AlertDialogExitoso(
                    onDismissRequest = {
                        dialogResultWebScraping2 = false
                        viewModel.hideDialogWebScraping()
                    },
                    onConfirmation = {
                        dialogResultWebScraping2 = false
                        viewModel.hideDialogWebScraping()
                    },
                    dialogTitle = "Proceso completado",
                    dialogText = "Respuesta del servidor: ${(motosUpdateState as Response.Success<String>).data}",
                    gifResourceId = R.drawable.gif_confirmar,
                    mostrarBotonAdd = true,
                    mostrarDatos = {
                        dialogResultWebScraping2 = false
                        viewModel.hideDialogWebScraping()
                        navHostController.navigate(NavAgregarRegistroScreen.RegistrarMotoWebScrapping.route)
                    }
                )
            }
        }

        is Response.Failure -> {
            // Muestra un mensaje de error
            Text("Error al actualizar las motos: ${(motosUpdateState as Response.Failure).exception?.message ?: "Error desconocido"}")
        }
    }
}