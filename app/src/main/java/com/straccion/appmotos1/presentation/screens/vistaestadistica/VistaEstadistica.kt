package com.straccion.appmotos1.presentation.screens.vistaestadistica

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.straccion.appmotos1.presentation.screens.vistaestadistica.components.GetDatosEstadistica

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun VistaEstadistica(
){
    Scaffold(
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())) {
                GetDatosEstadistica()
            }
        }
    )
}