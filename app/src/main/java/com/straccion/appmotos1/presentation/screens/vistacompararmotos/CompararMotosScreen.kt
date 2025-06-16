package com.straccion.appmotos1.presentation.screens.vistacompararmotos

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.components.GetMotosComparar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun CompararMotosScreen(
){
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                GetMotosComparar(paddingValues)
            }
        }
    )
}