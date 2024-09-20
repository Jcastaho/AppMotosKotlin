package com.straccion.appmotos1.compararmotosmenu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.straccion.appmotos1.CategoriaMotos
import com.straccion.appmotos1.CategoryItem
import com.straccion.appmotos1.ImagenMoto
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.R

@Composable
fun CompararMotosMenu(
    state: MotosState,
    viewModel: MotosViewModel
) {
    var seleccionados by remember { mutableStateOf(List(3) { MotoSeleccionada() }) }

    LazyColumn {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(3) { index ->
                    ObjetoComparacion(
                        state = state,
                        modifier = Modifier.weight(1f),
                        viewModel = viewModel,
                        motoSeleccionada = seleccionados[index],
                        onMotoSeleccionada = { moto, ficha ->
                            seleccionados = seleccionados.toMutableList().also {
                                it[index] = MotoSeleccionada(moto, ficha)
                            }
                        }
                    )
                }
            }
        }
    }
}

data class MotoSeleccionada(
    val moto: String? = null,
    val fichaItems: List<Pair<String, Any>> = emptyList()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ObjetoComparacion(
    state: MotosState,
    modifier: Modifier = Modifier,
    viewModel: MotosViewModel,
    motoSeleccionada: MotoSeleccionada,
    onMotoSeleccionada: (String, List<Pair<String, Any>>) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var imagenMoto by remember { mutableStateOf<String>("") }

    Card(
        modifier = modifier
            .padding(4.dp)
            .clickable { showBottomSheet = true }
            .fillMaxHeight(),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = 2.dp,
            color = if (motoSeleccionada.moto != null) MaterialTheme.colorScheme.primary else Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
            ) {
                if (motoSeleccionada.moto != null) {
                    ImagenMotos(
                        url = imagenMoto
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Seleccionar objeto",
                        modifier = Modifier
                            .size(40.dp)
                            .align(Alignment.Center),
                        tint = Color.DarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = motoSeleccionada.moto ?: "Seleccionar",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            motoSeleccionada.fichaItems.forEachIndexed { index, entry ->
                val (clave, valor) = entry
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (index % 2 == 0) Color.LightGray else Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = clave.replaceFirstChar { it.uppercase() },
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = valor.toString(),
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Selecciona una moto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        LazyColumn {
                            item {
                                state.filteredMotos.forEach { moto ->
                                    Text(
                                        text = moto.id,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                viewModel.selectMotoById(moto.id)
                                                imagenMoto = moto.imagenesPrincipales.firstOrNull() ?: ""
                                                viewModel.fichaItemsMostrar.value.let { fichaItems ->
                                                    onMotoSeleccionada(moto.id, fichaItems)
                                                }
                                                showBottomSheet = false
                                            }
                                            .padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImagenMotos(url: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                .fillMaxSize(),
            contentScale = ContentScale.Fit // Recorta la imagen para llenar el espacio
        )
    }
}