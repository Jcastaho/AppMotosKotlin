package com.straccion.appmotos1.presentation.screens.vistabasededatos.agregar.database_agregar_manual

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubirImagenesScreen(
    navController: NavHostController,
    viewModel: SubirImagenesViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    // Launcher para seleccionar multiples imágenes
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris: List<Uri> ->
            // Necesitamos saber a qué sección añadir las imágenes.
            // Esto se manejará con un estado temporal.
        }
    )

    // Estado para saber qué botón de "Añadir" se presionó
    var currentSection by remember { mutableStateOf("") }
    var currentColor by remember { mutableStateOf<String?>(null) }

    val multiImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris: List<Uri> ->
        uris.forEach { uri ->
            viewModel.addImage(currentSection, uri, currentColor)
        }
    }

    Scaffold(
        topBar = {
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Continuar al Formulario") },
                icon = { if(state.isUploading) CircularProgressIndicator(modifier=Modifier.size(24.dp)) else Icon(Icons.Default.Add, "")},
                onClick = { viewModel.uploadImagesAndContinue(navController) },
                expanded = !state.isUploading,
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ImageUploadSection(
                    title = "Imágenes Principales",
                    uris = state.imagenesPrincipales,
                    onAddClick = {
                        currentSection = "principales"
                        currentColor = null
                        multiImagePicker.launch("image/*")
                    },
                    onRemoveClick = { uri -> viewModel.removeImage("principales", uri) }
                )
            }
            item {
                ImageUploadSection(
                    title = "Imágenes de Características",
                    uris = state.caracteristicasImagenes,
                    onAddClick = {
                        currentSection = "caracteristicas"
                        currentColor = null
                        multiImagePicker.launch("image/*")
                    },
                    onRemoveClick = { uri -> viewModel.removeImage("caracteristicas", uri) }
                )
            }
            item {
                ColorManagementSection(
                    onAddColor = { color -> viewModel.addColor(color) }
                )
            }
            items(state.imagenesPorColor.keys.toList()) { color ->
                ImageUploadSection(
                    title = "Imágenes para el color: $color",
                    uris = state.imagenesPorColor[color] ?: emptyList(),
                    onAddClick = {
                        currentSection = "color"
                        currentColor = color
                        multiImagePicker.launch("image/*")
                    },
                    onRemoveClick = { uri -> viewModel.removeImage("color", uri, color) },
                    onDeleteSection = { viewModel.removeColor(color) }
                )
            }
            item { Spacer(modifier = Modifier.height(80.dp)) } // Espacio para el FAB
        }
    }
}
@Composable
fun ImageUploadSection(
    title: String,
    uris: List<Uri>,
    onAddClick: () -> Unit,
    onRemoveClick: (Uri) -> Unit,
    onDeleteSection: (() -> Unit)? = null
) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                if (onDeleteSection != null) {
                    IconButton(onClick = onDeleteSection) {
                        Icon(Icons.Default.Delete, contentDescription = "Eliminar sección de color", tint = MaterialTheme.colorScheme.error)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    AddImageButton(onClick = onAddClick)
                }
                items(uris) { uri ->
                    ImagePreview(uri = uri, onRemoveClick = { onRemoveClick(uri) })
                }
            }
        }
    }
}

@Composable
fun AddImageButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.AddPhotoAlternate, contentDescription = "Añadir imagen", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(40.dp))
    }
}

@Composable
fun ImagePreview(uri: Uri, onRemoveClick: () -> Unit) {
    Box(modifier = Modifier.size(100.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(uri).crossfade(true).build(),
            contentDescription = "Imagen seleccionada",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(8.dp))
        )
        IconButton(
            onClick = onRemoveClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(4.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .size(24.dp)
        ) {
            Icon(Icons.Default.Delete, contentDescription = "Quitar imagen", tint = Color.White, modifier = Modifier.size(16.dp))
        }
    }
}

@Composable
fun ColorManagementSection(onAddColor: (String) -> Unit) {
    var colorText by remember { mutableStateOf("") }
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Añadir Imágenes por Color", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = colorText,
                onValueChange = { colorText = it },
                label = { Text("Nombre del color (Ej: Rojo Fuego)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onAddColor(colorText)
                    colorText = ""
                },
                enabled = colorText.isNotBlank(),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Añadir Color")
            }
        }
    }
}