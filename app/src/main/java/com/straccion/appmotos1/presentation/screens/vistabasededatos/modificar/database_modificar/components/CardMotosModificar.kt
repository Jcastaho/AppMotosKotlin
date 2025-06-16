package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.database_modificar.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.navigation.screen.base_de_datos.NavModificarRegistroScreen
import com.straccion.appmotos1.presentation.navigation.screen.inicio.DrawerScreen
import com.straccion.appmotos1.presentation.navigation.screen.inicio.InicioScreen
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CardMotosModificar(
    navHostController: NavHostController,
    moto: CategoriaMotos
) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                navHostController.navigate(NavModificarRegistroScreen.EditarrRegistro.passEditarMoto(moto))
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), // Añade padding para separar el contenido de los bordes del Card
            verticalAlignment = Alignment.CenterVertically // Alinea vert
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .width(100.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    contentScale = ContentScale.Fit, // Recorta la imagen para llenar el espacio
                    model = moto.imagenesPrincipales.get(0),
                    contentDescription = "Moto image"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = moto.id,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Box(
                modifier = Modifier
                    .size(40.dp), // Ajusta el tamaño del espacio para el botón según lo necesites
                contentAlignment = Alignment.Center // Centra el contenido dentro del Box
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}