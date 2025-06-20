package com.straccion.appmotos1.presentation.screens.vistamimotoideal.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos

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
        LazyRow(
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(200.dp)
        ) {
            items(motos) { moto ->

                MMIdealCards(navHostController, moto)
            }
        }
    }
}