package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoViewModel
import com.straccion.appmotos1.presentation.screens.vistaestadistica.components.CardDialogInfoSelectMoto
import com.straccion.appmotos1.presentation.screens.vistainicio.components.Vista


@Composable
fun DialogSelectMoto(
    listas: Response<List<CategoriaMotos>>
) {
    // val listas by viewModel.allMotos.collectAsState()
    val context = LocalContext.current

    when (val response = listas) {
        Response.Loading -> {
            DefaultProgressBar()
        }

        is Response.Success -> {
            val motos = response.data
            Dialog(motos)
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

@Composable
fun Dialog(
    listas: List<CategoriaMotos>,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    var showDialog by viewModel.mostrarDialog
    Dialog(
        onDismissRequest = {
            showDialog = false
        } // Cerrar el diálogo al hacer clic fuera de él
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(
                    MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                ) // Fondo y forma del cuadro
                .padding(5.dp) // Espaciado interno
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Seleccione una moto".uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(5.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(listas) { moto ->
                        CardDialogInfoSelectMoto(moto)
                    }
                }
            }
        }
    }
}