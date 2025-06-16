package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoViewModel


@Composable
fun CardDialogInfoSelectMoto(
    moto: CategoriaMotos,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    var mostrarDialogSelectMoto by viewModel.mostrarDialog

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                viewModel.seleccionVs.value = moto
                mostrarDialogSelectMoto = false
            }
            .padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(width = 120.dp, height = 64.dp),
                contentScale = ContentScale.Fit, // Recorta la imagen para llenar el espacio
                model = moto.imagenesPrincipales.get(0),
                contentDescription = "Moto image"
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = moto.id,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
