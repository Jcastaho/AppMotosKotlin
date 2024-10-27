package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_modificar.components


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
fun VistaMotosModificar(
    motos: List<CategoriaMotos>,
    navHostController: NavHostController
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
            items(motos) { motos ->
                CardMotosModificar(navHostController, motos)
            }
        }
    }
}