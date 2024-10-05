package com.straccion.appmotos1.presentation.screens.vistabasededatos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField

@Composable
fun ModRegistro(
    state: MotosState,
    onMotoClick: (CategoriaMotos) -> Unit = {},
    onSearch: (String) -> Unit = {}
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
            state.filteredMotos.isEmpty() -> Text(
                "No se encontraron motos",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            else -> {
                Text(
                    "Número de motos: ${state.filteredMotos.size}",
                    modifier = Modifier.padding(6.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(state.filteredMotos) { moto ->
                        CategoryItems(moto, onClick = { onMotoClick(moto) })
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItems(motos: CategoriaMotos, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Añade padding para separar el contenido de los bordes del Card
            verticalAlignment = Alignment.CenterVertically // Alinea vert
        ) {
            ImagenMotos(
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
                    .size(40.dp), // Ajusta el tamaño del espacio para el botón según lo necesites
                contentAlignment = Alignment.Center // Centra el contenido dentro del Box
            ) {
                IconButton(onClick =  onClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Info",
                    )
                }
            }

        }
    }
}

@Composable
fun ImagenMotos(url: String) {
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
