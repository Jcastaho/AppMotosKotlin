package com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.components

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos


@Composable
fun VistaMotosElim(
    motos: List<CategoriaMotos>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Número de motos: ${motos.size}",
            modifier = Modifier.padding(10.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(motos.sortedBy { it.visible }) { motos ->
                CardMotosElim(motos)
            }
        }
    }
}