package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistaestadistica.EstadisticaViewModel

@Composable
fun CardsDialogInfo(
    motos: CategoriaMotos,
    viewModel: EstadisticaViewModel = hiltViewModel()
) {
    val maxVistas = viewModel.vistaMaxima
    val busquedaMaxima = viewModel.busquedaMaxima
    var showDialog3 by viewModel.mostrarDialog3


    // Calcular el porcentaje de progreso
    var progreso: Float

    if (showDialog3) {
        progreso = if (busquedaMaxima.value > 0) {
            motos.busquedas.toFloat() / busquedaMaxima.value.toFloat()
        } else {
            0f
        }
    } else {
        progreso = if (maxVistas.value > 0) {
            motos.vistas.toFloat() / maxVistas.value.toFloat()
        } else {
            0f
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = motos.id,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), // Añade padding para separar el contenido de los bordes del Card
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(width = 80.dp, height = 64.dp),
                    contentScale = ContentScale.Fit, // Recorta la imagen para llenar el espacio
                    model = motos.imagenesPrincipales.get(0),
                    contentDescription = "Moto image"
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(8.dp)
                        .background(Color.LightGray, RoundedCornerShape(4.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progreso)
                            .background(Color.Blue, RoundedCornerShape(4.dp))
                    )
                }

                Text(
                    text = if (showDialog3) "${motos.busquedas}" else "${motos.vistas}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

