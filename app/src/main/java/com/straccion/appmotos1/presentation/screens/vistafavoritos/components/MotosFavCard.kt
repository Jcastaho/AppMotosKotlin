package com.straccion.appmotos1.presentation.screens.vistafavoritos.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.components.DefaultAsyncImage
import com.straccion.appmotos1.presentation.navigation.DrawerScreen
import com.straccion.appmotos1.presentation.screens.vistafavoritos.FavoritosViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MotosFavCard(
    moto: CategoriaMotos,
    isSelected: Boolean,
    onClick: () -> Unit,
    onLongPress: () -> Unit,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val selected by viewModel.selectedMotos.collectAsState()

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(0.7f)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongPress
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DefaultAsyncImage(
                    url = moto.imagenesPrincipales.get(0)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = moto.id,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = moto.marcaMoto,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold
                )
            }

            // Ícono de selección
            if (selected.isNotEmpty()) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Seleccionado",
                        tint = Color.Blue,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.RadioButtonUnchecked,
                        contentDescription = "No seleccionado",
                        tint = Color.Gray,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}