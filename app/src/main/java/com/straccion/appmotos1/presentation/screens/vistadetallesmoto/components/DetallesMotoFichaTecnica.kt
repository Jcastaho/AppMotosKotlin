package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.DetallesMotoViewModel

@Composable
fun DetallesMotoFichaTecnica(
    moto: CategoriaMotos
) {
    val fichaTexnica = moto.fichaTecnica.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.padding(top = 15.dp, bottom = 5.dp),
            text = "Ficha Tecnica".uppercase(),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp
            )
        )
        Divider(
            modifier = Modifier.padding(bottom = 15.dp),
            color = Color.Black,
            thickness = 1.dp
        )


        fichaTexnica.forEachIndexed { index, _ ->
            val (clave, valor) = fichaTexnica[index]
            val claveCapitalizada =
                clave.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

            val backgroundColor = if (index % 2 == 0) Color.LightGray else Color.White

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp, bottom = 10.dp, top = 10.dp, start = 4.dp)
                        .align(Alignment.CenterVertically),
                    text = claveCapitalizada,
                    textAlign = TextAlign.Left,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 16.sp
                    )

                )
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, bottom = 10.dp, top = 10.dp)
                        .align(Alignment.CenterVertically),
                    text = valor.toString(),
                    textAlign = TextAlign.Left,
                    color = Color.Black,
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}