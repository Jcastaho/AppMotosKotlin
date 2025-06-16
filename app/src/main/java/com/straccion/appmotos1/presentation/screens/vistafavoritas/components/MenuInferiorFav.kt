package com.straccion.appmotos1.presentation.screens.vistafavoritas.components

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.presentation.screens.vistafavoritas.FavoritosViewModel

@Composable
fun MenuInferiorFav(
    context: Context,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val motosSeleccionadas by viewModel.motosSeleccionadas.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = WindowInsets.navigationBars.getBottom(LocalDensity.current).dp)
            .height(160.dp),  // Altura fija para el sheet
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.compartir(context,motosSeleccionadas)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Compartir"
                    )
                }
                Text(text = "Compartir")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                IconButton(
                    onClick = {
                        viewModel.update()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Eliminar"
                    )
                }
                Text(text = "Eliminar")
            }
        }
    }
}