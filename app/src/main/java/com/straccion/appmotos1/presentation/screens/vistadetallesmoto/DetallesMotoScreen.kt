package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.DetallesMotoContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetallesMotoScreen(viewModel: DetallesMotoViewModel = hiltViewModel()) {
    Scaffold (
        content = {
            DetallesMotoContent(viewModel)
        }
    )



}