package com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.R
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.AlertDialogExitoso
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistabasededatos.eliminar_registros.ElimRegistroViewModel

@Composable
fun ElimMotoDialog(
    viewModel: ElimRegistroViewModel = hiltViewModel()
) {
    val showDialog by viewModel.showConfirmDialog.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(viewModel.eliminarMotoResponse) {
        if (viewModel.eliminarMotoResponse is Response.Success) {
            viewModel.showDialogForLimitedTime()
        }
    }
    when (val response = viewModel.eliminarMotoResponse) {
        Response.Loading -> {
            DefaultProgressBar()
        }
        is Response.Failure -> {
            Toast.makeText(
                context,
                response.exception?.message ?: "Error desconocido",
                Toast.LENGTH_LONG
            ).show()
        }
        else -> {}
    }
    if (showDialog){
        AlertDialogExitoso(
            onDismissRequest = { viewModel.hideDialog() },
            onConfirmation = { viewModel.hideDialog() },
            dialogTitle = "Información",
            dialogText = "La moto se ha borrado correctamente",
            gifResourceId = R.drawable.gif_confirmar,
            mostrarBoton = false
        )
    }
}