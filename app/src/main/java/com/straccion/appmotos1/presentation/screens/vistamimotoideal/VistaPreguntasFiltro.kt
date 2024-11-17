package com.straccion.appmotos1.presentation.screens.vistamimotoideal

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.MotosViewModel
import com.straccion.appmotos1.QuestionnaireState


@Composable
fun VistaPreguntasFiltro(
    viewModel: MotosViewModel,
    questionnaireState: QuestionnaireState,
    onMotoClick: (CategoriaMotos) -> Unit = {},
    viewModel2: MMIdealViewModel = hiltViewModel()

) {
    val filteredMotos by viewModel.motosRecomendadas.collectAsState()
    val questions = viewModel2.questions


    if (!questionnaireState.isCompleted) {
        // Mostrar las preguntas mientras no se haya completado el cuestionario
        Preguntas(
            questions = questions,
            onAnswerSelected = { questionIndex, answer ->
                // Va haciendo las preguntas
                viewModel.handleAnswer(questionIndex, answer)
            },
            onComplete = {
                viewModel.completeQuestionnaire()
            }
        )
    } else {
        // Mostrar las tarjetas de motos y el título
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp) // Añadir padding alrededor
        ) {
            item {
                // Título "Categoría"
                Text(
                    text = "Motos recomendadas",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                val motosPorCategoria = filteredMotos.groupBy { it.ubicacionImagenes["carpeta3"] } // Ubicación de la categoría

                motosPorCategoria.forEach { (categoria, motos) ->
                    // Mostrar el título de la categoría
                    Text(
                        text = categoria ?: "Error",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    // Mostrar las motos de esta categoría
                    MostrarMotos(motos = motos, onClick = { selectedMoto ->
                        onMotoClick(selectedMoto)
                    })
                }
                if (filteredMotos.isEmpty()) {
                    // Texto de no hay recomendaciones
                    Text(
                        text = "No hay motos recomendadas.",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
fun MostrarMotos(motos: List<CategoriaMotos>, onClick: (CategoriaMotos) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth() // Llenar solo el ancho del padre
    ) {
        Text(
            "Número de motos: ${motos.size}",
            modifier = Modifier.padding(6.dp)
        )
        LazyHorizontalGrid(
            rows = GridCells.Fixed(1),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .height(200.dp) // Limitar la altura del LazyHorizontalGrid
        ) {
            items(
                motos
            ) { moto ->
                cartasMotos(moto, onClick = { onClick(moto) })
            }
        }
    }
}
@Composable
fun cartasMotos(motos: CategoriaMotos, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(4.dp)
            .width(260.dp) // Set a fixed width
            .wrapContentHeight()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImagendeMoto(
                url = motos.imagenesPrincipales[0],
                modifier = Modifier
                    .height(200.dp) // Set a fixed height for the image
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun ImagendeMoto(url: String, modifier: Modifier = Modifier) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .crossfade(true)
                .build()
        ),
        contentDescription = "Moto image",
        modifier = modifier,
        contentScale = ContentScale.Fit
    )
}