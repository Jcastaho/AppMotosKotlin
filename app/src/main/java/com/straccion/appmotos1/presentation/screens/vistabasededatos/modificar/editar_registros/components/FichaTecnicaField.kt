package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.presentation.components.DefaultOutlinedTextField

@Composable
fun FichaTecnicaField(
    clave: String,
    valor: String,
    onClaveChange: (String) -> Unit,
    onValorChange: (String) -> Unit,
    onFocusChange: (Boolean) -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        DefaultOutlinedTextField(
            value = clave,
            onValueChange = onClaveChange,
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                }
        )
        DefaultOutlinedTextField(
            value = valor,
            onValueChange = onValorChange,
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp)
                .onFocusChanged {
                    onFocusChange(it.isFocused)
                }
        )
        IconButton(onClick = onDelete) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Eliminar campo",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}