package com.straccion.appmotos1.presentation.screens.vistamimotoideal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.cartasMotos

@Composable
fun MMIdealMostarMotos(
    navHostController: NavHostController,
    motos: List<CategoriaMotos>
) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Llenar solo el ancho del padre
    ) {
        Text(
            "Número de motos: ${motos.size}",
            modifier = Modifier.padding(6.dp)
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(200.dp) // Limitar la altura del LazyHorizontalGrid
        ) {
            items(motos) { moto ->
                MMIdealCards(navHostController, moto)
            }
        }
    }
}