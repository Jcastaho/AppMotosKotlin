package com.straccion.appmotos1.presentation.screens.vistainicio.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.components.DefaultAsyncImage
import com.straccion.appmotos1.presentation.navigation.DrawerScreen
import com.straccion.appmotos1.presentation.screens.vistainicio.VistaInicioViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MotosCard(
    navHostController: NavHostController,
    motos: CategoriaMotos,
    viewModel: VistaInicioViewModel = hiltViewModel()
) {
    val busqueda = viewModel.busqueda
    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .clickable{
                val encodedMoto = URLEncoder.encode(motos.toJson(), StandardCharsets.UTF_8.toString())
                val id = motos.id
                val idEnconde = URLEncoder.encode(id, StandardCharsets.UTF_8.toString())
                val bus = busqueda.isNotEmpty() // devuelve true si busqueda no esta vacio
                navHostController.navigate(
                    route = DrawerScreen.Inicio.DetallesMoto.passMotos(encodedMoto, idEnconde, bus)
                )
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultAsyncImage(
                url = motos.imagenesPrincipales.get(0)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = motos.id,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(top = 10.dp))
            Text(
                text = motos.marcaMoto,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold
            )
        }
    }
}