package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles

import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.straccion.appmotos1.R
import com.straccion.appmotos1.domain.model.FavoritasUsuarios
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.presentation.components.DefaultProgressBar
import com.straccion.appmotos1.presentation.navigation.screen.versusmotos.NavVersusMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components.AmpliarInfiniteImage
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components.Detalles
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components.DialogSelectMoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesMotoScreen(
    navHostController: NavHostController,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val selectedImage by viewModel.selectedImage.collectAsState()
    val moto by viewModel.moto.collectAsState()
    val motosFavoritas by viewModel.motosFavoritas.collectAsState()
    var mostrarBoton by remember { mutableStateOf(false) }
    var isComparing by remember { mutableStateOf(false) }
    val imageLoaded = remember { mutableStateOf(false) }
    var mostrarDialogSelectMoto by viewModel.mostrarDialog
    val esModoOscuro = isSystemInDarkTheme()
    val listas by viewModel.allMotos.collectAsState()

    val seleccionVs by viewModel.seleccionVs.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    if (mostrarBoton) mostrarBoton = false
                    if (isComparing) isComparing = false
                },
                indication = null,
                interactionSource = remember { MutableInteractionSource() } // <- Esto evita que se detecte como interacción visible
            ),

        ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Detalles de Moto") },
                    navigationIcon = {
                        IconButton(onClick = { navHostController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Volver"
                            )
                        }
                    },
                    actions = {
                        when (val response = motosFavoritas) {
                            Response.Loading -> {
                                DefaultProgressBar()
                            }

                            is Response.Success -> {
                                val motosFav = response.data
                                val prueba: List<FavoritasUsuarios> = motosFav.toList()
                                val resultado = prueba.filter { it.motoId.contains(moto.id) }
                                IconButton(onClick = {
                                    if (resultado.isNotEmpty()) {
                                        viewModel.quitarMotoFav(moto.id, prueba)
                                    } else {
                                        viewModel.agregarMotoFav(moto.id)
                                    }
                                }) {
                                    if (resultado.isNotEmpty()) {
                                        Icon(
                                            imageVector = Icons.Filled.Star,
                                            contentDescription = "Quitar de favoritos"
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Filled.StarBorder,
                                            contentDescription = "Agregar a favoritos"
                                        )
                                    }
                                }
                            }

                            else -> ""
                        }
                    }
                )
            },
            floatingActionButton = {
                when {
                    !mostrarBoton -> {
                        FloatingActionButton(
                            modifier = Modifier
                                .size(40.dp)
                                .offset(y = (-20).dp, x = (-15).dp),
                            containerColor = MaterialTheme.colorScheme.onSurface,
                            onClick = {
                                mostrarBoton = true
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(3000)
                                    mostrarBoton = false
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_vs),
                                contentDescription = "Agregar campo",
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }

                    else -> {
                        Button(
                            onClick = {
                                if (!isComparing) isComparing = true
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(2000)
                                    mostrarBoton = false
                                }
                            },
                            modifier = Modifier.padding(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
                        ) {
                            Text("Comparar")
                        }
                    }
                }
            },
            content = { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues)
                ) {
                    Detalles(moto)
                }
            }
        )
        selectedImage?.let { (url, index) ->
            AmpliarInfiniteImage(
                imageUrl = url,
                imageIndex = index,
                moto = moto,
                onDismiss = { viewModel.clearSelectedImage() }
            )
        }

        if (isComparing) {
            var animateImages by remember { mutableStateOf(false) }
            var animationDuration by remember { mutableIntStateOf(300) }

            val imageOffset by animateDpAsState(
                targetValue = (-30).dp,
                animationSpec = keyframes {
                    durationMillis = animationDuration
                    0.dp at 0 using LinearEasing
                    (-30).dp at 150 using LinearEasing
                    (-20).dp at 200 using LinearEasing
                    (-30).dp at 250 using LinearEasing
                    0.dp at 300 using LinearEasing
                }
            )

            val vsScale by animateFloatAsState(
                targetValue = 4.0f,
                animationSpec = keyframes {
                    durationMillis = animationDuration
                    3.8f at (animationDuration / 2) using EaseOut
                    4.0f at animationDuration
                }
            )

            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { isComparing = false }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(16.dp),
                ) {
                    Column(
                        Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                modifier = Modifier
                                    .size(130.dp)
                                    .offset(x = if (animateImages) -imageOffset else 0.dp)
                                    .background(Color.Transparent)
                                    .scale(1.07f),
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(moto.imagenesPrincipales.first())
                                    .allowHardware(true)
                                    .crossfade(true)
                                    .size(Size.ORIGINAL)
                                    .memoryCachePolicy(CachePolicy.ENABLED)
                                    .diskCachePolicy(CachePolicy.ENABLED)
                                    .networkCachePolicy(CachePolicy.ENABLED)
                                    .memoryCacheKey(moto.imagenesPrincipales.first())
                                    .diskCacheKey(moto.imagenesPrincipales.first())
                                    .listener(
                                        onSuccess = { _, _ ->
                                            imageLoaded.value = true
                                        }
                                    )
                                    .build(),
                                contentScale = ContentScale.Fit,
                                contentDescription = "Moto image"
                            )

                            Box(
                                modifier = Modifier
                                    .scale(if (animateImages) vsScale else 1.8f)
                                    .zIndex(1f) // Esto pone el texto encima de las imágenes
                            ) {
                                Text(
                                    text = "vs",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            if (seleccionVs.id.isNotEmpty()) {
                                AsyncImage(
                                    modifier = Modifier
                                        .size(125.dp)
                                        .background(Color.Transparent)
                                        .offset(x = if (animateImages) imageOffset else 0.dp)
                                        .clickable { mostrarDialogSelectMoto = true }
                                        .scale(1.07f),
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(seleccionVs.imagenesPrincipales.first())
                                        .allowHardware(true)
                                        .crossfade(true)
                                        .size(Size.ORIGINAL)
                                        .memoryCachePolicy(CachePolicy.ENABLED)
                                        .diskCachePolicy(CachePolicy.ENABLED)
                                        .networkCachePolicy(CachePolicy.ENABLED)
                                        .memoryCacheKey(seleccionVs.imagenesPrincipales.first())
                                        .diskCacheKey(seleccionVs.imagenesPrincipales.first())
                                        .listener(
                                            onSuccess = { _, _ ->
                                                imageLoaded.value = true
                                            }
                                        )
                                        .build(),
                                    contentScale = ContentScale.Fit,
                                    contentDescription = "Moto image"
                                )
                            } else {
                                if (esModoOscuro){
                                    Image(
                                        modifier = Modifier
                                            .size(115.dp)
                                            .clickable { mostrarDialogSelectMoto = true },
                                        painter = painterResource(R.drawable.ic_sumar_sinfondo_black),
                                        contentDescription = "Seleccionar objeto"
                                    )
                                }else {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_sumar_sinfondo),
                                        contentDescription = "Seleccionar objeto",
                                        modifier = Modifier
                                            .size(115.dp)
                                            .clickable { mostrarDialogSelectMoto = true },
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Button(
                                onClick = {
                                    viewModel.clearSelectionVs()
                                    isComparing = false
                                },
                                modifier = Modifier.padding(16.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
                            ) {
                                Text("Cancelar")
                            }
                            Button(
                                onClick = {
                                    if (seleccionVs.id.isNotEmpty()) {
                                        animateImages = true
                                        animationDuration = 250
                                        CoroutineScope(Dispatchers.Main).launch {
                                            delay(animationDuration.toLong()) // Esperar la duración de la animación
                                            viewModel.clearSelectionVs()
                                            animateImages = false
                                            isComparing = false
                                            val id1 = moto.id
                                            val id2 = seleccionVs.id
                                            navHostController.navigate(
                                                NavVersusMotos.VersusMotos.passMotosVs(
                                                    id1,
                                                    id2
                                                )
                                            )
                                        }
                                    }
                                },
                                modifier = Modifier.padding(16.dp),
                                colors =
                                    ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
                            ) {
                                Text("VS")
                            }
                        }
                    }
                }
            }
        }
        if (mostrarDialogSelectMoto) {
            DialogSelectMoto(
                listas = listas
            )
        }
        if (isComparing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)) // Fondo semitransparente
            )
        }
    }
}
