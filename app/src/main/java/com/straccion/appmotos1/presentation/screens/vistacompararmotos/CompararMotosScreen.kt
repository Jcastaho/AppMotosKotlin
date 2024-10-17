package com.straccion.appmotos1.presentation.screens.vistacompararmotos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.components.GetMotosComparar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompararMotosScreen(
){
    Scaffold(
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                GetMotosComparar()
            }
        }
    )
}