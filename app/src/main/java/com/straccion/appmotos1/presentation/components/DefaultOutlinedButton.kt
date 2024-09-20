package com.straccion.appmotos1.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp


@Composable
fun DefaultOutlinedButton(
    modifier: Modifier,
    text: String,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primary,
) {
    OutlinedButton(
        onClick = { onClick() },
        modifier = modifier
    ) {
        Text(
            text = text,
            color = color,
            style = TextStyle(
                fontSize = 22.sp
            )
        )
    }
}