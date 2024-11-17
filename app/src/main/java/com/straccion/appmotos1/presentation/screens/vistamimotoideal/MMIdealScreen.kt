package com.straccion.appmotos1.presentation.screens.vistamimotoideal

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.straccion.appmotos1.presentation.screens.vistamimotoideal.components.MMIdealMostarMotos


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MMIdealScreen(
    navHostController: NavHostController,
    viewModel: MMIdealViewModel = hiltViewModel()
) {
    val filteredMotos by viewModel.motosRecomendadas.collectAsState()
    val questions = viewModel.questions
    val questionnaireState by viewModel.questionnaireState.collectAsState()


    Scaffold(
        content = {
            Box() {
                //muestra todas las preguntas
                if (!questionnaireState.isCompleted) {
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
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            val motosPorCategoria =
                                filteredMotos.groupBy { it.ubicacionImagenes["carpeta3"] } // Ubicación de la categoría

                            motosPorCategoria.forEach { (categoria) ->
                                // Mostrar el título de la categoría
                                Text(
                                    text = categoria ?: "Error",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )

                                // Mostrar las motos de esta categoría
                                MMIdealMostarMotos(navHostController, filteredMotos)
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
        }
    )
}