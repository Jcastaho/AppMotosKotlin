package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.editar_registros.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.editar_registros.EditarRegistrosViewModel

@Composable
fun EditarMotosScreen(
    moto: CategoriaMotos,
    viewModel: EditarRegistrosViewModel = hiltViewModel()
) {
    val fichaItems by viewModel.fichaState.collectAsState()
    val scrollState = rememberScrollState()
    val imagenPrincipal = moto.imagenesPrincipales

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagenMotoEditar(imagenPrincipal)
        FichaTecnicaEditar(fichaItems)
        AddCasillasEditar(viewModel::agregarNuevoCampo)
        ActualizarButton(){
            viewModel.actualizar(moto.id, fichaItems)
        }
    }
}