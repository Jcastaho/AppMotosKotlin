package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.AmpliarInfiniteImage
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.Detalles
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components.DetallesMotoContent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetallesMotoScreen(
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val selectedImage by viewModel.selectedImage.collectAsState()
    val motos by viewModel.moto.collectAsState()
    Scaffold (
        content = {
            Detalles(motos)
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