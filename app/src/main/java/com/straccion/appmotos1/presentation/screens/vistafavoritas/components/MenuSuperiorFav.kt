package com.straccion.appmotos1.presentation.screens.vistafavoritas.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuSuperiorFav(
    selectedCount: Int,
    onCancelSelection: () -> Unit,
    onSelectAll: () -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(onClick = onCancelSelection) {
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .padding(start = 3.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "Cancelar"
            )
        }

        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            fontSize = 15.sp,
            text = "$selectedCount elementos seleccionados"
        )

        IconButton(onClick = onSelectAll) {
            Icon(
                modifier = Modifier
                    .size(35.dp)
                    .padding(end = 3.dp),
                imageVector = Icons.Default.Checklist,
                contentDescription = "Seleccionar todo"
            )
        }
    }
}