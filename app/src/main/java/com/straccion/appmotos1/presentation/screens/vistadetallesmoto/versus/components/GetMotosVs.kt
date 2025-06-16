package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.components

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.VersusMotoViewModel


@Composable
fun GetMotosVs(
    viewModel: VersusMotoViewModel = hiltViewModel()
) {
    val moto1 by viewModel.moto1.collectAsState()
    val moto2 by viewModel.moto2.collectAsState()
    val context = LocalContext.current

    when {
        moto1 is Response.Loading || moto2 is Response.Loading -> {
            DefaultProgressBar()
        }
        moto1 is Response.Failure -> {
            Toast.makeText(
                context,
                (moto1 as Response.Failure).exception?.message ?: "Error desconocido en Moto 1",
                Toast.LENGTH_LONG
            ).show()
        }
        moto2 is Response.Failure -> {
            Toast.makeText(
                context,
                (moto2 as Response.Failure).exception?.message ?: "Error desconocido en Moto 2",
                Toast.LENGTH_LONG
            ).show()
        }
        moto1 is Response.Success && moto2 is Response.Success -> {
            VersusMotoCompare(
                (moto1 as Response.Success).data,
                (moto2 as Response.Success).data
            )
        }
    }
}