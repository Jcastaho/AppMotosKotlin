package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_modificar

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField
import com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_modificar.components.GetMotosModificar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ModRegistroScreen(
    navHostController: NavHostController,
    viewModel: ModRegistrosViewModel = hiltViewModel(),
    ) {
    val busqueda = viewModel.busqueda
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                DefaultOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    value = busqueda,
                    onValueChange = {
                        //aqui va la funcion de busqueda
                        viewModel.onSearchQueryChanged(it)
                    },
                    label = "Buscar motos",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Search
                    )
                )
            }
        },
        content = { paddingValues ->
            // Pasar el paddingValues para evitar que el contenido se superponga con el topBar
            Box(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
                GetMotosModificar(navHostController)
            }
        }
    )
}