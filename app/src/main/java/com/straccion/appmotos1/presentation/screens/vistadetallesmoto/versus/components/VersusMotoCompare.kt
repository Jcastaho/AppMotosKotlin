package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.versus.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.straccion.appmotos1.R
import com.straccion.appmotos1.domain.model.CategoriaMotos

@Composable
fun VersusMotoCompare(
    moto1: CategoriaMotos, moto2: CategoriaMotos
) {
    val imageLoaded = remember { mutableStateOf(false) }

    //estado de color
    var colorMoto by remember { mutableStateOf(Color.Blue) }

    //estado de motoSeleccionada, si es true es moto1 y si es false es moto2
    var selectedMoto by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier
                    .width(140.dp)
                    .height(195.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(125.dp)
                        .background(Color.Transparent)
                        .scale(1.07f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(moto1.imagenesPrincipales.first())
                        .allowHardware(true)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .memoryCacheKey(moto1.imagenesPrincipales.first())
                        .diskCacheKey(moto1.imagenesPrincipales.first())
                        .listener(
                            onSuccess = { _, _ ->
                                imageLoaded.value = true
                            }
                        )
                        .build(),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Moto image"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = moto1.id.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
            Column(
                modifier = Modifier
                    .width(140.dp)
                    .height(195.dp)
                    .padding(end = 40.dp)
                    .weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(125.dp)
                        .background(Color.Transparent)
                        .scale(1.07f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(moto2.imagenesPrincipales.first())
                        .allowHardware(true)
                        .crossfade(true)
                        .size(Size.ORIGINAL)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .memoryCacheKey(moto2.imagenesPrincipales.first())
                        .diskCacheKey(moto2.imagenesPrincipales.first())
                        .listener(
                            onSuccess = { _, _ ->
                                imageLoaded.value = true
                            }
                        )
                        .build(),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Moto image"
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = moto2.id.uppercase(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center

                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Button(
                onClick = {
                    colorMoto = Color.Blue
                    selectedMoto = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue
                ),
            ) {
                Text(
                    text = moto1.id.uppercase(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Button(
                onClick = {
                    colorMoto = Color.Cyan
                    selectedMoto = false
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
            ) {
                Text(
                    text = moto2.id.uppercase(),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(15.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardColors(
                contentColor = colorMoto,
                containerColor = colorMoto,
                disabledContentColor = MaterialTheme.colorScheme.onSurface,
                disabledContainerColor = MaterialTheme.colorScheme.onSurface
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "¿Por que la " + if (selectedMoto) moto1.id else moto2.id + " es mejor?",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorMoto == Color.Cyan) Color.Black else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Por que si y esta mela",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorMoto == Color.Cyan) Color.Black else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Por que si y esta mela",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorMoto == Color.Cyan) Color.Black else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Por que si y esta mela",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorMoto == Color.Cyan) Color.Black else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Por que si y esta mela",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (colorMoto == Color.Cyan) Color.Black else Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "ESPECIFICACIONES",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        val cilindraje1 = obtenerValorNumerico(moto1.fichaTecnica, "cilindr")
        val cilindraje2 = obtenerValorNumerico(moto2.fichaTecnica, "cilindr")

        VersusMotoCardsBarras(
            titulo = "CILINDRAJE",
            complemento = "CC",
            icono = painterResource(id = R.drawable.icon_cilindraje),
            valor1 = cilindraje1,
            valor2 = cilindraje2,
            valorDividir = 200
        )
        val potencia1 = obtenerValorNumerico(moto1.fichaTecnica, "potenci")
        val potencia2 = obtenerValorNumerico(moto2.fichaTecnica, "potenci")
        VersusMotoCardsBarras(
            titulo = "POTENCIA",
            complemento = "HP",
            icono = painterResource(id = R.drawable.ic_pistones),
            valor1 = potencia1,
            valor2 = potencia2,
            valorDividir = 50
        )

        val torque1 = obtenerValorNumerico(moto1.fichaTecnica, "torque")
        val torque2 = obtenerValorNumerico(moto2.fichaTecnica, "torque")

        VersusMotoCardsBarras(
            titulo = "TORQUE",
            complemento = "Nm",
            icono = painterResource(id = R.drawable.ic_llave_inglesa),
            valor1 = torque1,
            valor2 = torque2,
            valorDividir = 100
        )

        val peso1 = obtenerValorNumerico(moto1.fichaTecnica, "peso")
        val peso2 = obtenerValorNumerico(moto2.fichaTecnica, "peso")

        VersusMotoCardsBarras(
            titulo = "PESO",
            complemento = "KG",
            icono = painterResource(id = R.drawable.ic_peso),
            valor1 = peso1,
            valor2 = peso2,
            valorDividir = 50
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "FRENOS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        VersusMotoCardsTexto(
            titulo = "ABS",
            iconoTitulo = painterResource(id = R.drawable.icon_abs),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        VersusMotoCardsTexto(
            titulo = "TIPO DE FRENOS",
            iconoTitulo = painterResource(id = R.drawable.icon_freno),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "RENDIMIENTO",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        VersusMotoCardsTexto(
            titulo = "VELOCIDAD MAXIMA",
            iconoTitulo = painterResource(id = R.drawable.icon_freno),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        VersusMotoCardsTexto(
            titulo = "CONSUMO APROXIMADO POR GALÓN",
            iconoTitulo = painterResource(id = R.drawable.icon_freno),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
        ) {
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "OTROS",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        VersusMotoCardsTexto(
            titulo = "SISTEMA DE ALIMENTACIÓN",
            iconoTitulo = painterResource(id = R.drawable.icon_freno),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        VersusMotoCardsTexto(
            titulo = "ENCENDIDO",
            iconoTitulo = painterResource(id = R.drawable.icon_encendido),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        VersusMotoCardsTexto(
            titulo = "PRECIO",
            iconoTitulo = painterResource(id = R.drawable.icon_preciomoto),
            texto1 = "prueba",
            texto2 = "prueba",
            chulo1 = true,
            chulo2 = false
        )
        Spacer(modifier = Modifier.height(55.dp))
    }
}

fun obtenerValorNumerico(fichaTecnica: Map<String, Any>, clave: String): Double {
    val claveEncontrada = fichaTecnica.keys.find { it.contains(clave, ignoreCase = true) }
    val valorString = claveEncontrada?.let { fichaTecnica[it] as? String } ?: "0"
    return "\\d+(\\.\\d+)?".toRegex().find(valorString)?.value?.toDoubleOrNull() ?: 0.0
}