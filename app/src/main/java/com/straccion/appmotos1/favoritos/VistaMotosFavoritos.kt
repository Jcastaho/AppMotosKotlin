package com.straccion.appmotos1.favoritos

import android.content.res.Configuration
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.straccion.appmotos1.CategoriaMotos
import com.straccion.appmotos1.CategoryItem
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.MotosViewModel


@Composable
fun VistaMotosFavoritos(
    state: MotosState,
    onMotoClick: (CategoriaMotos) -> Unit,
    viewModel: MotosViewModel
) {

    val configuration = LocalConfiguration.current
    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 3 // Apaisado
        else -> 2 // Vertical o por defecto
    }

    // Filtramos las motos favoritas de manera reactiva
    val motosFavoritas by remember(state.filteredMotos, state.motosFavoritas) {
        derivedStateOf {
            state.filteredMotos.filter { moto -> state.motosFavoritas.contains(moto.id) }
        }
    }
    val selectedItems by viewModel.itemsSeleccionados.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            )

            state.errorMessage != null -> Text(state.errorMessage, color = Color.Red)
            motosFavoritas.isEmpty() -> Text(
                "No se encontraron motos favoritas",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            else -> {
                if (selectedItems.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = { viewModel.deselectAllMotos() }) {
                            Icon(Icons.Default.Close, contentDescription = "Cancelar selección")
                        }
                        Text("${selectedItems.size} seleccionados", modifier = Modifier.padding(top = 10.dp))  // Actualiza automáticamente según la selección
                        TextButton(onClick = { viewModel.selectAllMotos(motosFavoritas) }) {
                            Text("Seleccionar todo")
                        }
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = motosFavoritas,
                        key = { it.id } // Asumiendo que CategoriaMotos tiene un id único
                    ) { moto ->
                        val isSelected = selectedItems.contains(moto)
                        Category(
                            moto,
                            isSelected = isSelected,
                            onLongClick = { viewModel.toggleMotoSelection(moto) },
                            onClick = {
                                if (selectedItems.isNotEmpty()) {
                                    viewModel.toggleMotoSelection(moto)
                                }else{
                                    onMotoClick(moto)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Category(
    motos: CategoriaMotos,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) Color.Blue else Color.Transparent,
        animationSpec = tween(durationMillis = 300)
    )
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .border(2.dp, borderColor, RoundedCornerShape(6.dp))
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = { onLongClick() },
                    onTap = { onClick() }
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagenMoto(
                url = motos.imagenesPrincipales.get(0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = motos.id,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = motos.marcaMoto,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ImagenMoto(url: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f) // Esto hará que el contenedor sea cuadrado
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(url)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "Moto image",
            modifier = Modifier
                .fillMaxSize()
                .scale(1.07f), // Llena todo el espacio disponible
            contentScale = ContentScale.Fit // Recorta la imagen para llenar el espacio
        )
    }
}
