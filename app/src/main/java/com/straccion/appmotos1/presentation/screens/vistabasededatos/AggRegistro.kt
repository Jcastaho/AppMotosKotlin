package com.straccion.appmotos1.presentation.screens.vistabasededatos

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.R
import com.straccion.appmotos1.presentation.components.DefaultButton
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AggRegistro(viewModel: MotosViewModel) {
    val state by viewModel.state.collectAsState()

    var fabricanteText by remember { mutableStateOf(false) }
    var marcaText by remember { mutableStateOf(false) }
    var categoriaText by remember { mutableStateOf(false) }
    var nombreMotoaRegistrar by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.listaMarcas()
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
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
                                    value = state.selectedFrabricante ?: "",
                                    onValueChange = { },
                                    label = stringResource(R.string.fabricante),
                                    readOnly = true,
                                    onIconClick = { fabricanteText = true },
                                    icon = Icons.Filled.ArrowDropDown
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { categoriaText = true }
                            ) {
                                DefaultOutlinedTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = state.selectedMarca ?: "",
                                    onValueChange = { },
                                    label = stringResource(R.string.marca),
                                    readOnly = true,
                                    onIconClick = { marcaText = true },
                                    icon = Icons.Filled.ArrowDropDown
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable { categoriaText = true }
                        ) {
                            DefaultOutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                value = state.selectedCategoriasMotos ?: "",
                                onValueChange = { },
                                label = stringResource(R.string.categoria),
                                readOnly = true,
                                onIconClick = { categoriaText = true },
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
                                value = state.nombreMotoaRegistrar?.takeIf { it.isNotEmpty() }
                                    ?: "",
                                onValueChange = {
                                    nombreMotoaRegistrar = it
                                    viewModel.selectOption("nombre", it)
                                } ,
                                label = stringResource(R.string.nombreMoto),
                                onIconClick = { /*TODO*/ },
                                icon = null
                            )
                        }
                    }
                }
            }
            item{
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
                            onClick = {  },
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
                            onClick = {  },
                        )
                    }
                }
            }
        }
    }
    if (fabricanteText) {
        ModalBottomSheet(
            onDismissRequest = { fabricanteText = false }
        ) {
            LazyColumn {
                items(state.listaFrabricanteMotos.distinct()) { fabricante ->
                    ListItem(
                        headlineContent = { Text(fabricante) },
                        modifier = Modifier.clickable {
                            viewModel.selectOption("fabricante", fabricante)
                            fabricanteText = false
                        }
                    )
                }
            }

        }
    }

    if (marcaText) {
        ModalBottomSheet(
            onDismissRequest = { marcaText = false }
        ) {
            LazyColumn() {
                items(state.listaMarcasMotos.distinct()) { marca ->
                    ListItem(
                        headlineContent = { Text(marca) },
                        modifier = Modifier
                            .clickable {
                                viewModel.selectOption("marca", marca)
                                marcaText = false
                            }
                    )
                }
            }
        }
    }
    if (categoriaText) {
        ModalBottomSheet(
            onDismissRequest = { categoriaText = false },
        ) {
            LazyColumn {
                items(state.listaCategoriasMotos.distinct()) { categoria ->
                    ListItem(
                        headlineContent = { Text(categoria) },
                        modifier = Modifier.clickable {
                            viewModel.selectOption("categoria", categoria)
                            categoriaText = false
                        }
                    )
                }
            }
        }
    }
}