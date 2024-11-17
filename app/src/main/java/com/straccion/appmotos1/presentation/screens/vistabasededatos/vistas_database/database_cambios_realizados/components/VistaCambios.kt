package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados.components

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.domain.model.MotoDiferencias

@Composable
fun VistaCambios(
    motos: List<MotoDiferencias>
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ){
        Row() {
            Text("Nombre de la Moto")
            Spacer(modifier = Modifier.padding(15.dp))
            Text("Dato anterior")
            Spacer(modifier = Modifier.padding(15.dp))
            Text("Dato Cambiado")
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 15.dp)
        ) {
            items(motos) { moto ->
                ListaDeCambios(moto = moto)
            }
        }
    }
}