package com.straccion.appmotos1.presentation.screens.vistabasededatos

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HideImage
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.straccion.appmotos1.presentation.components.AlertDialogExitoso
import com.straccion.appmotos1.presentation.components.AlertDialogPregunta
import com.straccion.appmotos1.CategoriaMotos
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.presentation.components.DefaultIconButton
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField

@Composable
fun ElimRegistro(
    state: MotosState,
    viewModel: MotosViewModel,
    onSearch: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        DefaultOutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp),
            value = state.searchQuery,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            onValueChange = { query ->
                onSearch(query)
            },
            label = "Buscar motos"
        )        
        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            )

            state.errorMessage != null -> Text(state.errorMessage, color = Color.Red)
            state.motos.isEmpty() -> Text(
                "No se encontraron motos",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            else -> {
                Text(
                    "Número de motos: ${state.motos.size}",
                    modifier = Modifier.padding(6.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        if (state.searchQuery.isEmpty()) {
                            state.motos.sortedBy { it.visible }
                        } else {
                            state.filteredMotos.sortedBy { it.visible }
                        }
                    ) { moto ->
                        ItemsCategory(moto, viewModel, state)
                    }
                }
            }
        }
    }
}


@Composable
fun ItemsCategory(motos: CategoriaMotos, viewModel: MotosViewModel, state: MotosState) {
    var showConfirmDialog by remember { mutableStateOf(false) }
    var showConfirmDialog2 by remember { mutableStateOf(false) }

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
            ImagenMotoss(
                url = motos.imagenesPrincipales.get(0)
            )
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
                            onClick = { showConfirmDialog2 = true },
                            icon = Icons.Default.HideImage,
                            contentDescription = "Ocultar"
                        )
                    } else {
                        DefaultIconButton(
                            onClick = { showConfirmDialog2 = true },
                            icon = Icons.Default.RemoveRedEye,
                            contentDescription = "Ocultar"
                        )
                    }
                    DefaultIconButton(
                        onClick = { showConfirmDialog = true },
                        icon = Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
                // Evitar que el diálogo se muestre repetidamente
                if (showConfirmDialog2) {
                    var title = ""
                    var text = ""
                    if (motos.visible){
                        title = "Ocultar Moto"
                        text = "¿Estás seguro de que deseas ocultar esta motocicleta?"
                    }else{
                        title = "Mostrar Moto"
                        text = "¿Estás seguro de que deseas volver a mostrar esta motocicleta?"
                    }
                    AlertDialogPregunta(
                        onDismissRequest = { showConfirmDialog2 = false },
                        onConfirmation = {
                            showConfirmDialog2 = false
                            viewModel.actualizarOEliminarMoto(motos.id, MotosViewModel.AccionMoto.ACTUALIZAR_VISIBILIDAD,!motos.visible)
                        },
                        dialogTitle = title,
                        dialogText = text,
                        icon = Icons.Filled.Info
                    )
                }
                if (showConfirmDialog) {
                    AlertDialogPregunta(
                        onDismissRequest = { showConfirmDialog = false },
                        onConfirmation = {
                            showConfirmDialog = false
                            viewModel.actualizarOEliminarMoto(motos.id, MotosViewModel.AccionMoto.ELIMINAR)
                        },
                        dialogTitle = "Confirmar Eliminación",
                        dialogText = "¿Estás seguro de que deseas eliminar el documento?",
                        icon = Icons.Filled.Info
                    )
                }
                if (state.dialogInfo?.isSuccess == true) {
                    state.dialogInfo.let { dialogInfo ->
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
        }
    }
}


@Composable
fun ImagenMotoss(url: String) {
    Box(
        modifier = Modifier
            .height(80.dp)
            .width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "Moto image",
            contentScale = ContentScale.Fit // Recorta la imagen para llenar el espacio
        )
    }
}
