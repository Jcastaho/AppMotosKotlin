package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.components.GetMotosVs

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersusMotoScreen(
    navHostController: NavHostController
) {
    Scaffold(
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                GetMotosVs()
            }
        }
    )
}