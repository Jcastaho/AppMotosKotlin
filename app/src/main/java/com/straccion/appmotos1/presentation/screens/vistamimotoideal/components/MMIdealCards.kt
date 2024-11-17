package com.straccion.appmotos1.presentation.screens.vistamimotoideal.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.navigation.DrawerScreen
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.ImagendeMoto
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MMIdealCards(
    navHostController: NavHostController,
    motos: CategoriaMotos
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(260.dp) // Set a fixed width
            .wrapContentHeight()
            .clickable{
                val encodedMoto = URLEncoder.encode(motos.toJson(), StandardCharsets.UTF_8.toString())
                val id = motos.id
                val idEnconde = URLEncoder.encode(id, StandardCharsets.UTF_8.toString())
                navHostController.navigate(
                    route = DrawerScreen.Inicio.DetallesMoto.passMotos(encodedMoto, idEnconde, false)
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagendeMoto(
                url = motos.imagenesPrincipales[0],
                modifier = Modifier
                    .height(200.dp) // Set a fixed height for the image
                    .fillMaxWidth()
            )
        }
    }
}