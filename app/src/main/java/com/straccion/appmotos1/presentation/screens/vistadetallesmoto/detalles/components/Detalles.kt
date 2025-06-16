package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoViewModel

@Composable
fun Detalles(
    moto: CategoriaMotos,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val motosResponse by viewModel.motoById.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(true) {
        viewModel.sumarVisitas(moto.id)
    }

    when (val response = motosResponse) {
        Response.Loading -> {
            DefaultProgressBar()
        }
        is Response.Success -> {
            DetallesMotoContent(moto)
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