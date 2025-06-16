package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VersusMotoCardsBarras(
    titulo: String,
    complemento: String,
    icono: Painter,
    valor1: Double,
    valor2: Double,
    valorDividir:Int
    ) {
    val progresoMoto1 = valor1.formatAsString()
    val progresoMoto2 = valor2.formatAsString()

    val maxValor = maxOf(valor1, valor2)+valorDividir
    val barraProgresoMoto1 = if (maxValor != 0.0) (valor1 / maxValor).toFloat() else 0f
    val barraProgresoMoto2 = if (maxValor != 0.0) (valor2 / maxValor).toFloat() else 0f

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardColors(
            contentColor = MaterialTheme.colorScheme.surface,
            containerColor = MaterialTheme.colorScheme.surface,
            disabledContentColor = MaterialTheme.colorScheme.onSurface,
            disabledContainerColor = MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = titulo,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Image(
                    modifier = Modifier
                        .size(40.dp),
                    painter = icono,
                    contentDescription = ""
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "$progresoMoto1 $complemento",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // Altura fija para el contenedor de las barras
                ) {
                    // Barra gris claro (fondo)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth() // Ocupa todo el ancho
                            .height(20.dp) // Misma altura que la barra de progreso
                            .background(Color.LightGray, RoundedCornerShape(4.dp)) // Color gris claro
                    )

                    // Barra de progreso (color cian)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(barraProgresoMoto1) // Ancho proporcional al progreso
                            .height(20.dp) // Misma altura que la barra de fondo
                            .background(Color.Blue, RoundedCornerShape(4.dp)) // Color cian
                    )
                }
            }
            Spacer(modifier = Modifier.height(25.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "$progresoMoto2 $complemento",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp) // Altura fija para el contenedor de las barras
                ) {
                    // Barra gris claro (fondo)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp)) // Color gris claro
                    )

                    // Barra de progreso (color cian)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(barraProgresoMoto2) // Ancho proporcional al progreso
                            .height(20.dp) // Misma altura que la barra de fondo
                            .background(Color.Cyan, RoundedCornerShape(4.dp)) // Color cian
                    )
                }
            }
        }
    }
}


fun Double.formatAsString(): String {
    return if (this == this.toLong().toDouble()) {
        // Si es un valor entero, lo convierte a Long y luego a String
        this.toLong().toString()
    } else {
        // Si tiene decimales, lo deja como Double y lo convierte a String
        this.toString()
    }
}