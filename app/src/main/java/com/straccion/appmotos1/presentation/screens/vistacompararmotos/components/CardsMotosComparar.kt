package com.straccion.appmotos1.presentation.screens.vistacompararmotos.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.MotoSeleccionada
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardsMotosComparar(
    modifier: Modifier = Modifier,
    index: Int,
    motoSeleccionada: MotoSeleccionada,
    viewModel: CompararMotosViewModel = hiltViewModel()
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val motosDisponibles by viewModel.motosDisponibles.collectAsState()

    Card(
        modifier = modifier
            .padding(2.dp)
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
                    ImagenMotoComparar(url = motoSeleccionada.moto.imagenesPrincipales.firstOrNull() ?: "")
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
                modifier = Modifier
                    .padding(bottom = 5.dp),
                text = motoSeleccionada.moto?.id ?: "Seleccionar",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            motoSeleccionada.moto?.fichaTecnica?.toList()?.forEachIndexed { index, (clave, valor) ->
                FichaTecnicaComparar(clave, valor, index % 2 == 0)
            }
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
                    items(motosDisponibles) { moto ->
                        Text(
                            text = moto.id,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.seleccionarMoto(index, moto)
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


