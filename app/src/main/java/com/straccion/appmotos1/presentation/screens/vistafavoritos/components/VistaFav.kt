package com.straccion.appmotos1.presentation.screens.vistafavoritos.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.navigation.DrawerScreen
import com.straccion.appmotos1.presentation.screens.vistafavoritos.FavoritosViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun VistaFav(
    motos: List<CategoriaMotos>,
    navHostController: NavHostController,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val selectedMotos by viewModel.selectedMotos.collectAsState()

    val configuration = LocalConfiguration.current
    val columns = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> 3 // Apaisado
        else -> 2 // Vertical o por defecto
    }

    val isSelectionMode = selectedMotos.isNotEmpty()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(motos) { moto ->
                MotosFavCard(
                    moto = moto,
                    isSelected = selectedMotos.contains(moto.id),
                    onLongPress = { viewModel.toggleMotoSelection(moto.id) },
                    onClick = {
                        if (isSelectionMode) {
                            viewModel.toggleMotoSelection(moto.id)
                        } else {
                            // Navegar a detalles
                            val encodedMoto = URLEncoder.encode(moto.toJson(), StandardCharsets.UTF_8.toString())
                            val id = moto.id
                            val idEncoded = URLEncoder.encode(id, StandardCharsets.UTF_8.toString())
                            navHostController.navigate(
                                route = DrawerScreen.Inicio.DetallesMoto.passMotos(encodedMoto, idEncoded, false)
                            )
                        }
                    }
                )
            }
        }
    }
}