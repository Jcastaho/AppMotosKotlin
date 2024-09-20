package com.straccion.appmotos1.vistabasededatos

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.R


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
                                OutlinedTextField(
                                    value = state.selectedFrabricante ?: "",
                                    onValueChange = { },
                                    label = { Text(stringResource(R.string.fabricante)) },
                                    readOnly = true,
                                    trailingIcon = {
                                        IconButton(onClick = { fabricanteText = true }) {
                                            Icon(Icons.Filled.ArrowDropDown, "Expandir")
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { categoriaText = true }
                            ) {
                                OutlinedTextField(
                                    value = state.selectedMarca ?: "",
                                    onValueChange = { },
                                    trailingIcon = {
                                        IconButton(onClick = { marcaText = true }) {
                                            Icon(Icons.Filled.ArrowDropDown, "Expandir")
                                        }
                                    },
                                    label = { Text(stringResource(R.string.marca)) },
                                    readOnly = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp)
                                .clickable { categoriaText = true }
                        ) {
                            OutlinedTextField(
                                value = state.selectedCategoriasMotos ?: "",
                                onValueChange = { },
                                trailingIcon = {
                                    IconButton(onClick = { categoriaText = true }) {
                                        Icon(Icons.Filled.ArrowDropDown, "Expandir")
                                    }
                                },
                                label = { Text(stringResource(R.string.categoria)) },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 30.dp)
                        ) {
                            OutlinedTextField(
                                value = state.nombreMotoaRegistrar?.takeIf { it.isNotEmpty() }
                                    ?: "",
                                onValueChange = {
                                    nombreMotoaRegistrar = it
                                    viewModel.selectOption("nombre", it)
                                },
                                label = { Text(stringResource(R.string.nombreMoto)) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 12.dp)
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
                        Button(
                            onClick = { /* Acción del botón */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Usamos containerColor en vez de backgroundColor
                            contentPadding = PaddingValues(),
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
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Registrar Moto Manual")
                        }
                        Button(
                            onClick = {  },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Usamos containerColor en vez de backgroundColor
                            contentPadding = PaddingValues(),
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
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text("Registrar Moto Web Scraping")
                        }
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