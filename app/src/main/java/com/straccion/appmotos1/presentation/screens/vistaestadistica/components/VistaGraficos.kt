package com.straccion.appmotos1.presentation.screens.vistaestadistica.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.presentation.screens.vistaestadistica.EstadisticaViewModel

@Composable
fun VistaGraficos(
    viewModel: EstadisticaViewModel = hiltViewModel()
) {
    val listaMasVistas by viewModel.motosMasVistas.collectAsState()
    val listaMenosVistas by viewModel.motosMenosVistas.collectAsState()
    val listaMasBuscadas by viewModel.motosMasBuscadas.collectAsState()

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
        GraficoMotosMasVistas(listaMasVistas)
        GraficoMotosMenosVistas(listaMenosVistas)
        GraficoMotosMasBuscadas(listaMasBuscadas)
    }


}