package com.straccion.appmotos1.presentation.screens.vistainicio.components

import android.content.res.Configuration
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.MotosState
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistainicio.VistaInicioViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun Vista(
    onMotoClick: (CategoriaMotos) -> Unit,
    viewModel: VistaInicioViewModel = hiltViewModel()
) {
    val motosResponse by viewModel.motosResponse.collectAsState()
    val context = LocalContext.current

    val configuration = LocalConfiguration.current
    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 3 // Apaisado
        else -> 2 // Vertical o por defecto
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        when (val response = motosResponse) {
            Response.Loading -> {
                DefaultProgressBar()
            }
            is Response.Success -> {
                val motos = response.data
                Text(
                    text = "Número de motos: ${motos.size}",
                    modifier = Modifier.padding(16.dp)
                )
                LazyVerticalGrid(
                    columns = GridCells.Fixed(columns),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(motos) { moto ->
                        MotosCard(moto, onClick = { onMotoClick(moto) })
                    }
                }
            }
            is Response.Failure -> {
                Toast.makeText(
                    context,
                    response.exception?.message ?: "Error desconocido",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}