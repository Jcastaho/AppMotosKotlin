package com.straccion.appmotos1.presentation.screens.vistafavoritas.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistafavoritas.FavoritosViewModel

@Composable
fun GetMotosFavoritas(
    navHostController: NavHostController,
    viewModel: FavoritosViewModel = hiltViewModel()
) {
    val motosResponse by viewModel.motosFavoritas.collectAsState()
    val context = LocalContext.current

    when (val response = motosResponse) {
        Response.Loading -> {
            DefaultProgressBar()
        }
        is Response.Success -> {
            val motos = response.data
            VistaFav(motos, navHostController)
        }
        is Response.Failure -> {
            Toast.makeText(
                context,
                response.exception?.message ?: "Error desconocido",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}