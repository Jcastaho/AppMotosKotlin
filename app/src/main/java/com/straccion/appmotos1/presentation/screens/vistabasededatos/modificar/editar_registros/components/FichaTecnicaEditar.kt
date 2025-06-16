package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.EditarRegistrosViewModel

@Composable
fun FichaTecnicaEditar(
    fichaItems: Map<String, Any>,
    viewModel: EditarRegistrosViewModel = hiltViewModel()
) {
    fichaItems.forEach { (originalKey, originalValue) ->
        var keyState by remember(originalKey) { mutableStateOf(originalKey) }
        var valueState by remember(originalKey) { mutableStateOf(originalValue.toString()) }
        var isFocused by remember { mutableStateOf(false) }

        FichaTecnicaField(
            clave = keyState,
            valor = valueState,
            onClaveChange = { newKey ->
                keyState = newKey
            },
            onValorChange = { newValue ->
                valueState = newValue
            },
            onFocusChange = { focused ->
                if (!focused && isFocused) {
                    viewModel.modificarCampo(originalKey, keyState, valueState)
                }
                isFocused = focused
            },
            onDelete = {
                viewModel.eliminarCampo(originalKey)
            }
        )
    }
}