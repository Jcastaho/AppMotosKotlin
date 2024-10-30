package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.AmpliarInfiniteImage
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.Detalles
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.DetallesMotoContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetallesMotoScreen(
    navHostController: NavHostController,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val selectedImage by viewModel.selectedImage.collectAsState()
    val motos by viewModel.moto.collectAsState()
    val motosFavoritas by viewModel.motosFavoritas.collectAsState()


    Box(modifier = Modifier.fillMaxWidth()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Detalles de Moto") },
                    navigationIcon = {
                        IconButton(onClick = { navHostController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    actions = {
                        when (val response = motosFavoritas) {
                            Response.Loading -> {
                                DefaultProgressBar()
                            }
                            is Response.Success -> {
                                val motosFav = response.data
                                val prueba: List<FavoritasUsuarios> = motosFav.toList()
                                val resultado = prueba.filter { it.motoId.contains(motos.id) }
                                IconButton(onClick = {
                                    if (resultado.isNotEmpty()) {

                                    }else{
                                        viewModel.agregarMotoFav(motos.id)
                                    }
                                }) {
                                    if (resultado.isNotEmpty()) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = "Quitar de favoritos"
                                        )
                                    }else{
                                        Icon(
                                            imageVector = Icons.Filled.StarBorder,
                                            contentDescription = "Agregar a favoritos"
                                        )
                                    }
                                }
                            }
                            else -> ""
                        }
                    }
                )
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    Detalles(motos)
                }
            }
        )
        selectedImage?.let { (url, index) ->
            AmpliarInfiniteImage(
                imageUrl = url,
                imageIndex = index,
                moto = motos,
                onDismiss = { viewModel.clearSelectedImage() }
            )
        }
    }
}