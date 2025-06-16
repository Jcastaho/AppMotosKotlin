package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.R
import com.straccion.appmotos1.presentation.components.DefaultButton
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavAgregarRegistroScreen
import com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar.AggRegistroViewModel

@Composable
fun VistaAggRegistro(
    navHostController: NavHostController,
    viewModel: AggRegistroViewModel = hiltViewModel()
) {
    val nombreMotoaRegistrar by viewModel.nombreMotoaRegistrar.collectAsState()
    val fabricanteText by viewModel.fabricanteText.collectAsState()
    val marcaText by viewModel.marcaText.collectAsState()
    val categoriaText by viewModel.categoriaText.collectAsState()


    Box(
        modifier = Modifier
            .fillMaxSize() ,
        contentAlignment = Alignment.Center // Centra el contenido dentro del Box
    ) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente los elementos
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.5f)
                        .align(Alignment.Center)
                ) {
                    Column(
                        modifier = Modifier
                            .border(
                                BorderStroke(2.dp, Color.Black),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp, bottom = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                DefaultOutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = fabricanteText.second,
                                    onValueChange = { },
                                    label = stringResource(R.string.fabricante),
                                    readOnly = true,
                                    onIconClick = { viewModel.mostrarFabricante() },
                                    icon = Icons.Filled.ArrowDropDown
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { viewModel.mostrarCategoria() }
                            ) {
                                DefaultOutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = marcaText.second,
                                    onValueChange = { },
                                    label = stringResource(R.string.marca),
                                    readOnly = true,
                                    onIconClick = { viewModel.mostrarMarca() },
                                    icon = Icons.Filled.ArrowDropDown
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable { viewModel.mostrarCategoria() }
                        ) {
                            DefaultOutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = categoriaText.second,
                                onValueChange = { },
                                label = stringResource(R.string.categoria),
                                readOnly = true,
                                onIconClick = { viewModel.mostrarCategoria() },
                                icon = Icons.Filled.ArrowDropDown
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        ) {
                            DefaultOutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp),
                                value = nombreMotoaRegistrar,
                                onValueChange = {
                                   viewModel.nombreMoto(it)
                                },
                                label = stringResource(R.string.nombreMoto),
                                onIconClick = { /*TODO*/ },
                                icon = null
                            )
                        }
                    }
                }
            }
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column {
                        DefaultButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(28.dp)) // Botón con bordes redondeados
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                                    )
                                )
                                .shadow(8.dp, RoundedCornerShape(28.dp)) // Sombra para efecto 3D
                                .align(Alignment.CenterHorizontally),
                            text = "Registrar Moto Manual",
                            onClick = {
                                navHostController.navigate(NavAgregarRegistroScreen.RegistrarMotoManual.route)
                            },
                            enabled = if(nombreMotoaRegistrar != "" &&
                                categoriaText.second != "" &&
                                marcaText.second != "" &&
                                fabricanteText.second != "") true else false
                        )
                        DefaultButton(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(28.dp)) // Botón con bordes redondeados
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                                    )
                                )
                                .shadow(8.dp, RoundedCornerShape(28.dp)) // Sombra para efecto 3D
                                .align(Alignment.CenterHorizontally),
                            text = "Registrar Moto Web Scraping",
                            onClick = {
                                viewModel.showDialogWebScraping()
                            },
                            enabled = nombreMotoaRegistrar != "" &&
                                categoriaText.second != "" &&
                                marcaText.second != "" &&
                                fabricanteText.second != ""
                        )
                    }
                }
            }
        }
    }
}