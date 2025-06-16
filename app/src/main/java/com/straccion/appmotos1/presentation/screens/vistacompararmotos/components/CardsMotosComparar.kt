package com.straccion.appmotos1.presentation.screens.vistacompararmotos.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    // Animación para el estado pressed
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Animación de escala
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(durationMillis = 100)
    )
    val busqueda = viewModel.busqueda

    Card(
        modifier = modifier
            .padding(2.dp) // Más padding para mejor separación
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                showBottomSheet = true
            }
            .fillMaxHeight()
            .scale(scale), // Animación de escala
        shape = RoundedCornerShape(12.dp), // Bordes más redondeados
        colors = CardDefaults.cardColors(
            containerColor = if (motoSeleccionada.moto != null) {
                MaterialTheme.colorScheme.surface
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
            },
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(
            pressedElevation = 16.dp,
            hoveredElevation = 12.dp
        ),
        border = if (motoSeleccionada.moto != null) {
            BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            )
        } else {
            BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
            ) {
                if (motoSeleccionada.moto != null) {
                    ImagenMotoComparar(
                        url = motoSeleccionada.moto.imagenesPrincipales.firstOrNull() ?: ""
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = busqueda,
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.Gray
                        )
                    },
                    onValueChange = {
                        //aqui va la funcion de busqueda
                        viewModel.onSearchQueryChanged(it)
                    },
                    placeholder = { Text(text = "Buscar motos") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    ),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true
                )
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


