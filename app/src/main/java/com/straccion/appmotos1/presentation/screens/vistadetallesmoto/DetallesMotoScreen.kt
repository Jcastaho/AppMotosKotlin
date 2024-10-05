package com.straccion.appmotos1.presentation.screens.vistadetallesmoto

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.straccion.appmotos1.MotosViewModel
import kotlinx.coroutines.delay
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetallesMotoScreen(viewModel: MotosViewModel) {
    val questionnaireState by viewModel.questionnaireState.collectAsState()
    val state by viewModel.state.collectAsState()
    val selectedMoto = state.selectedMotos
    val pagerState = rememberPagerState(pageCount = { state.imagenesMostradas.size })
    var selectedImage by remember { mutableStateOf<Pair<String, Int>?>(null) }

    // Si no hay una moto seleccionada, no hacemos nada
    LaunchedEffect(state.colorSeleccionado) {
        state.colorSeleccionado?.let { color ->
            viewModel.selectColor(color)
        }
    }

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
                    AsyncImage(
                        model = state.imagenesMostradas[page],
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
                    pageCount = state.imagenesMostradas.size,
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
                            items(selectedMoto?.colores ?: emptyList()) { color ->
                                val parsedColor = parseColor(color)
                                val isSelected = color == state.colorSeleccionado

                                Box(
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clip(CircleShape)
                                        .background(parsedColor)
                                        .border(
                                            3.dp,
                                            if (isSelected) Color.Black else Color.Transparent,
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
                    text = "${selectedMoto?.id}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 35.sp,
                        textAlign = TextAlign.Center // Alinea el texto al centro
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "${selectedMoto?.marcaMoto}",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    )
                )
                val modelos = selectedMoto!!.modelos.entries.toList()

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
                val formattedPrice = numberFormat.format(selectedMoto.precioActual ?: 0)

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
                    text = "${selectedMoto?.descripcion}",
                    textAlign = TextAlign.Justify,
                    style = TextStyle(
                        fontSize = 14.sp
                    )
                )
            }
            if (questionnaireState.personalizedRecommendation?.isNotEmpty() == true){
                Card   (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ){
                    Text(
                        text = "¿Por que es recomendable esta motocicleta para ti?",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = questionnaireState.personalizedRecommendation
                            ?: "",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 15.dp, start = 5.dp, end = 5.dp, bottom = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            InfiniteImageCarousel(
                viewModel = viewModel,
                onImageClick = { url, index -> selectedImage = url to index }
            )
        }
        item {
            FichaTecnica(viewModel)
        }
        item {
            MensajeFinal()
        }
    }
    selectedImage?.let { (url, index) ->
        ImageDetailScreen(
            imageUrl = url,
            imageIndex = index,
            onDismiss = { selectedImage = null },
            viewModel
        )
    }
}

@Composable
fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pageCount) { iteration ->
            val color = if (currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(8.dp)
            )
        }
    }
}

fun parseColor(colorString: String): Color {
    return when {
        colorString.startsWith("#") -> {
            try {
                Color(android.graphics.Color.parseColor(colorString))
            } catch (e: IllegalArgumentException) {
                Color.Gray // Color predeterminado si no se puede parsear
            }
        }

        else -> when (colorString.lowercase()) {
            "NEGRO" -> Color.Black
            "BLANCO" -> Color.White
            "ROJO" -> Color.Red
            "VERDE" -> Color.Green
            "AZUL" -> Color.Blue
            "AMARILLO" -> Color.Yellow
            // Agrega más colores según sea necesario
            else -> Color.Gray // Color predeterminado si no se reconoce el nombre
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfiniteImageCarousel(
    viewModel: MotosViewModel,
    onImageClick: (String, Int) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val pagerState = rememberPagerState(pageCount = { state.imagenesAdicionales.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == state.imagenesAdicionales.size - 1) {
                delay(4000)
                pagerState.animateScrollToPage(0)
            } else {
                delay(4000)
                pagerState.animateScrollToPage(page + 1)
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 65.dp)
    ) { page ->
        Card(
            modifier = Modifier
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).also { scale ->
                        scaleX = scale
                        scaleY = scale
                    }
                    alpha =
                        lerp(start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f))
                }
                .fillMaxWidth()
                .aspectRatio(1f)
                .clickable { onImageClick(state.imagenesAdicionales[page], page) },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = state.imagenesAdicionales[page],
                contentDescription = "Imagen ${page + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun ImageDetailScreen(
    imageUrl: String,
    imageIndex: Int,
    onDismiss: () -> Unit,
    viewModel: MotosViewModel
) {
    val state by viewModel.state.collectAsState()
    var imageLoadError by remember { mutableStateOf(false) }

    val imageRequest = ImageRequest.Builder(LocalContext.current)
        .data(imageUrl)
        .crossfade(true)
        .build()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable(onClick = onDismiss)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (imageLoadError) {
                Text(
                    text = "Error al cargar la imagen",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                AsyncImage(
                    model = imageRequest,
                    contentDescription = "Imagen detallada",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    onError = {
                        imageLoadError = true
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            val textoCaracteristicas = state.caracteristicasTexto.getOrNull(imageIndex)
            Text(
                text = textoCaracteristicas ?: "",
                textAlign = TextAlign.Justify,
                color = Color.White,
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth()
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 46.dp, end = 16.dp)
                .size(48.dp)
                .clickable(onClick = onDismiss)
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = "Cerrar",
                tint = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
fun FichaTecnica(
    viewModel: MotosViewModel
) {
    val fichaItems by viewModel.fichaItemsMostrar.collectAsState()

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
        //revisar codigo, funciona pero no se esta usanbdo entry
        fichaItems.forEachIndexed { index, entry ->
            val (clave, valor) = fichaItems[index]
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