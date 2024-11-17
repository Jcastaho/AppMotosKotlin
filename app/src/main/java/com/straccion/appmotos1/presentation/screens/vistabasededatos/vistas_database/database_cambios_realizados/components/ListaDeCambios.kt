package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_cambios_realizados.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.straccion.appmotos1.domain.model.MotoDiferencias
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive

@Composable
fun ListaDeCambios(
    moto: MotoDiferencias
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Mostrar ID
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "ID: ${moto.id}",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,

            )
            Spacer(modifier = Modifier.padding(5.dp))

            // Mostrar Status si existe
            moto.status?.let { status ->
                Text(
                    text = "Estado: $status",
                    style = MaterialTheme.typography.body1,
                    color = Color.Blue
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            // Mostrar las diferencias
            moto.differences.forEach { (key, difference) ->
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Campo: $key",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.padding(8.dp))
                // Mostrar el valor de webscraping
                when (difference.webscraping) {
                    is kotlinx.serialization.json.JsonPrimitive -> {
                        Column {
                            Text(
                                text = "Nuevo dato: ".uppercase(),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF197F10)
                            )
                            Text(
                                text = difference.webscraping.jsonPrimitive.content,
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    is kotlinx.serialization.json.JsonArray -> {

                        Column {
                            Text(
                                text = "Nuevos datos:".uppercase(),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF197F10)
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            Text(
                                text = difference.webscraping.jsonArray.joinToString(),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    }
                    is kotlinx.serialization.json.JsonObject -> {
                        Column {
                            Text(
                                text = "Nuevos datos:".uppercase(),
                                style = MaterialTheme.typography.body2,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF197F10)
                            )
                            Spacer(modifier = Modifier.padding(5.dp))
                            difference.webscraping.entries.forEach { (key, value) ->
                                Text(
                                    text = "$key: ${value.jsonPrimitive.contentOrNull ?: value.toString()}",
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        }
                    }
                    else -> ""
                }
                Spacer(modifier = Modifier.padding(10.dp))

                // Mostrar el valor de data
                Column {
                    Text(
                        text = "Dato anterior: ".uppercase(),
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    )
                    Text(
                        text = difference.data,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}