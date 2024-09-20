package com.straccion.appmotos1.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun DefaultOutlinedTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (value: String) -> Unit,
    label: String? = null,
    readOnly: Boolean = false,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default, // KeyboardOptions opcional
    onIconClick: (() -> Unit)? = null, // El onClick también es opcional
    icon: ImageVector? = null, // El ícono ahora es opcional (puede ser nulo)
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(label?: "") },
        readOnly = readOnly,
        singleLine = singleLine,
        trailingIcon = {
            if (icon != null && onIconClick != null) { // Solo muestra el ícono si se proporciona
                IconButton(onClick = { onIconClick() }) {
                    Icon(icon, contentDescription = "Trailing Icon")
                }
            }
        },
        keyboardOptions = keyboardOptions // Aquí puedes pasar las opciones de teclado
    )
}