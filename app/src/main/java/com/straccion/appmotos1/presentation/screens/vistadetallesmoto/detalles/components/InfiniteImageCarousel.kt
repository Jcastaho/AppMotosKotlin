package com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.presentation.screens.vistadetallesmoto.detalles.DetallesMotoViewModel
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue

@Composable
fun InfiniteImageCarousel(
    moto: CategoriaMotos,
    viewModel: DetallesMotoViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState(pageCount = { moto.caracteristicasImagenes.size })
    val imageLoaded = remember { mutableStateOf(false) }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page == moto.caracteristicasImagenes.size - 1) {
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
                .clickable { viewModel.selectedImage(moto.caracteristicasImagenes[page] to page) }, // Al hacer clic, se selecciona la imagen actual
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(moto.caracteristicasImagenes[page])
                    .allowHardware(true)
                    .crossfade(true)
                    .size(Size.ORIGINAL)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .memoryCacheKey(moto.caracteristicasImagenes[page])
                    .diskCacheKey(moto.caracteristicasImagenes[page])
                    .listener(
                        onSuccess = { _, _ ->
                            imageLoaded.value = true
                        }
                    )
                    .build(),
                contentDescription = "Imagen ${page + 1}",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}