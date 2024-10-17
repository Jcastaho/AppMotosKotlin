package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.DetallesMotoViewModel
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetallesMotoContent(
    moto: CategoriaMotos,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val displayedImages by viewModel.displayedImages.collectAsState()
    val pagerState = rememberPagerState(pageCount = { moto.imagenesPrincipales.size })
    val selectedColor by viewModel.selectedColor.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
                    .background(MaterialTheme.colorScheme.background)// Aquí estableces el fondo blanco
            )
            {
                HorizontalPager(
                    modifier = Modifier.fillMaxSize(),
                    state = pagerState
                ) { page ->
                    val imageUrl = displayedImages.getOrNull(page)
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Imagen de moto ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                PageIndicator(
                    pageCount = moto.imagenesPrincipales.size,
                    currentPage = pagerState.currentPage
                )
            }
        }
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ) {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(moto.colores) { color ->
                                val parsedColor = parseColor(color)
                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(parsedColor)
                                        .border(
                                            3.dp,
                                            if (color == selectedColor) Color.Black else Color.Transparent,
                                            CircleShape
                                        )
                                        .clickable {
                                            viewModel.selectColor(color)
                                        }
                                )

                            }
                        }
                    }
                }
            }
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()// Alinea el contenido del Box en el centro
            ) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = moto.id,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center // Alinea el texto al centro
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = moto.marcaMoto,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )
                val modelos = moto.modelos.entries.toList()

                if (modelos.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text(
                            text = modelos[0].value,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp
                            )
                        )
                        if (modelos.size > 1) {
                            Text(
                                text = modelos[1].value,
                                modifier = Modifier.padding(start = 8.dp),
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 22.sp
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
                val numberFormat = NumberFormat.getNumberInstance(Locale("es", "CO"))
                val formattedPrice = numberFormat.format(moto.precioActual)

                Text(
                    text = "$ $formattedPrice",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 45.sp
                    )
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(start = 12.dp, end = 12.dp, bottom = 14.dp),
                    text = moto.descripcion,
                    textAlign = TextAlign.Justify,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
//            if (questionnaireState.personalizedRecommendation?.isNotEmpty() == true){
//                Card   (
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(12.dp)
//                ){
//                    Text(
//                        text = "¿Por que es recomendable esta motocicleta para ti?",
//                        style = TextStyle(
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        ),
//                        modifier = Modifier
//                            .padding(top = 8.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
//                    Text(
//                        text = questionnaireState.personalizedRecommendation
//                            ?: "",
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .padding(top = 15.dp, start = 5.dp, end = 5.dp, bottom = 8.dp)
//                            .align(Alignment.CenterHorizontally)
//                    )
//                }
//            }

            InfiniteImageCarousel(moto)
        }
        item {
            DetallesMotoFichaTecnica(moto)
        }
        item {
            MensajeFinal()
        }
    }

}

fun parseColor(colorString: String): Color {
    val normalizedColor = colorString.uppercase().replace(Regex("[^A-Z]"), "")
    return when {
        colorString.startsWith("#") -> {
            try {
                Color(android.graphics.Color.parseColor(colorString))
            } catch (e: IllegalArgumentException) {
                Color.Gray // Color predeterminado si no se puede parsear
            }
        }

        else -> {
            when {
                normalizedColor.contains("NEGR") -> Color.Black
                normalizedColor.contains("BLANC") -> Color.White
                normalizedColor.contains("ROJ") -> Color.Red
                normalizedColor.contains("VERDE") -> Color.Green
                normalizedColor.contains("AZUL") -> Color.Blue
                normalizedColor.contains("AMARILL") -> Color.Yellow
                normalizedColor.contains("NARANJA") -> Color(0xFFFFA500)
                normalizedColor.contains("PURPURA") || normalizedColor.contains("MORADO") -> Color(0xFF800080)
                normalizedColor.contains("ROSA") -> Color(0xFFFFC0CB)
                normalizedColor.contains("MARRON") || normalizedColor.contains("CAFE") -> Color(0xFF8B4513)
                normalizedColor.contains("CYAN") || normalizedColor.contains("CIAN") -> Color.Cyan
                normalizedColor.contains("MAGENTA") || normalizedColor.contains("FUCSIA") -> Color.Magenta
                // Agrega más colores según sea necesario
                else -> Color.Gray // Color predeterminado si no se reconoce el nombre
            }
        }
    }
}


@Composable
fun MensajeFinal() {
    Text(
        modifier = Modifier.padding(top = 15.dp, bottom = 95.dp, start = 16.dp, end = 16.dp),
        text = "Esta información se obtiene de las páginas oficiales, le recomendamos verificar directamente en el sitio web del fabricante",
        style = TextStyle(
            fontSize = 12.sp
        ),
        textAlign = TextAlign.Center,
        color = Color.LightGray
    )
}

