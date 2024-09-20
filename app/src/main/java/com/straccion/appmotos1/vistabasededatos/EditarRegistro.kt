package com.straccion.appmotos1.vistabasededatos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.straccion.appmotos1.AlertDialogExitoso
import com.straccion.appmotos1.AlertDialogPregunta
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.MotosViewModel


@Composable
fun EditarRegistro(viewModel: MotosViewModel, state: MotosState) {
    val fichaItems by viewModel.fichaItemsEditar.collectAsState()
    val imagen = state.imagenesMostradas.getOrNull(0) // Evitar error de índice fuera de rango
    var imageLoadError by remember { mutableStateOf(false) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val selectedMoto = state.selectedMotos

    var isFocused by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    val isLoading by viewModel.isCargar.collectAsState()

    LaunchedEffect(selectedMoto?.id) {
        selectedMoto?.id?.let {
            viewModel.cargarDatos()
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        val scrollState = rememberScrollState()
        val imageRequest = imagen?.let {
            ImageRequest.Builder(LocalContext.current)
                .data(it)
                .crossfade(true)
                .build()
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    ),
                    textAlign = TextAlign.Center,
                    text = (selectedMoto?.id?.takeIf { !it.isNullOrEmpty() }
                        ?: "").toString()
                )
                if (imageLoadError || imagen == null) {
                    Text(
                        text = "Error al cargar la imagen",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    AsyncImage(
                        model = imageRequest,
                        contentDescription = "Imagen detallada",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        onError = {
                            imageLoadError = true
                        }
                    )
                }
                when {
                    isLoading -> CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 15.dp)
                    )

                    state.errorMessage != null -> Text(state.errorMessage, color = Color.Red)
                    state.filteredMotos.isEmpty() -> Text(
                        "Hubo un error",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    else -> {
                        fichaItems.forEachIndexed { index, (clave, valor) ->
                            val (clave, valor) = fichaItems[index]
                            var claveLocal by rememberSaveable { mutableStateOf(clave) }
                            var valorLocal by rememberSaveable { mutableStateOf(valor) }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.CenterHorizontally)
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    value = claveLocal,
                                    onValueChange = { nuevaClave ->
                                        claveLocal = nuevaClave
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(end = 4.dp)
                                        .align(Alignment.CenterVertically)
                                        .onFocusChanged { focusState ->
                                            if (!focusState.isFocused && isFocused) {
                                                // Solo actualiza cuando se pierde el foco y antes estaba enfocado
                                                viewModel.actualizarItem(
                                                    index,
                                                    claveLocal,
                                                    valorLocal
                                                )
                                            }
                                            isFocused =
                                                focusState.isFocused // Actualiza el estado del foco
                                        }
                                )
                                OutlinedTextField(
                                    value = valorLocal.toString(),
                                    onValueChange = { nuevoValor ->
                                        valorLocal = nuevoValor
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(start = 6.dp)
                                        .align(Alignment.CenterVertically)
                                        .onFocusChanged { focusState ->
                                            if (!focusState.isFocused && isFocused) {
                                                // Solo actualiza cuando se pierde el foco y antes estaba enfocado
                                                viewModel.actualizarItem(
                                                    index, claveLocal, parseValue(
                                                        valorLocal.toString(), valor
                                                    )
                                                )
                                            }
                                            isFocused =
                                                focusState.isFocused // Actualiza el estado del foco
                                        }
                                )
                                IconButton(
                                    onClick = {
                                        viewModel.eliminarCampo(index)
                                    },
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    Icon(Icons.Default.Close, contentDescription = "Eliminar campo")
                                }
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    FloatingActionButton(
                        onClick = { viewModel.agregarNuevoCampo() },
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Agregar campo")
                    }
                }

                Button(
                    onClick = {
                        focusManager.clearFocus()
                        showConfirmDialog = true
                    },
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
                )
                {
                    Text(text = "Actualizar")
                }
            }
        }
    }
    if (showConfirmDialog) {
        AlertDialogPregunta(
            onDismissRequest = { showConfirmDialog = false },
            onConfirmation = {
                showConfirmDialog = false
                viewModel.actualizarFichaTecnica()
            },
            dialogTitle = "Confirmar actualización",
            dialogText = "¿Estás seguro de que quieres actualizar la ficha técnica?",
            icon = Icons.Filled.Info
        )
    }
    if (state.showDialog) {
        state.dialogInfo?.let { dialogInfo ->
            AlertDialogExitoso(
                onDismissRequest = { viewModel.dismissDialog() },
                onConfirmation = { viewModel.dismissDialog() },
                dialogTitle = dialogInfo.title,
                dialogText = dialogInfo.message,
                gifResourceId = dialogInfo.gifResourceId
            )
        }
    }
}

fun parseValue(input: String, originalValue: Any): Any {
    return when (originalValue) {
        is Int -> input.toIntOrNull() ?: 0
        is Boolean -> input.toLowerCase() == "true"
        else -> input
    }
}