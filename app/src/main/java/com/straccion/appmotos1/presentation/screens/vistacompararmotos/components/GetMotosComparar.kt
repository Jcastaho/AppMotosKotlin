package com.straccion.appmotos1.presentation.screens.vistacompararmotos.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistacompararmotos.CompararMotosViewModel


@Composable
fun GetMotosComparar(
    paddingValues: PaddingValues,
    viewModel: CompararMotosViewModel = hiltViewModel(),
) {
    val motosResponse by viewModel.motosResponse.collectAsState()
    val context = LocalContext.current


    when (val response = motosResponse) {
        Response.Loading -> {
            DefaultProgressBar()
        }
        is Response.Success -> {
            val seleccionados by viewModel.motosSeleccionadas.collectAsState()
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        repeat(3) { index ->
                            CardsMotosComparar(
                                modifier = Modifier.weight(1f),
                                index = index,
                                motoSeleccionada = seleccionados[index]
                            )
                        }
                    }
                }
            }
        }
        is Response.Failure -> {
            Toast.makeText(
                context,
                response.exception?.message ?: "Error desconocido",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}