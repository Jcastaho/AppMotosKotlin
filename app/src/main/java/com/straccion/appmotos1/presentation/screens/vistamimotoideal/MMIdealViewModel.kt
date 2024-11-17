package com.straccion.appmotos1.presentation.screens.vistamimotoideal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.QuestionnaireState
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MMIdealViewModel @Inject constructor(
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
) : ViewModel() {

    //obtener las motos a mostrar
    private val _motosResponse = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()

    init {
        getMotos()
    }

    private fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            obtenerMotosUsesCase.obtenerMotosVisibles().collect { response ->
                _motosResponse.value = response
            }
        } catch (e: Exception) {
            _motosResponse.value = Response.Failure(e)
        }
    }

    private val _questionnaireState = MutableStateFlow(QuestionnaireState())
    val questionnaireState = _questionnaireState.asStateFlow()

    private val _motosRecomendadas = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosRecomendadas: StateFlow<List<CategoriaMotos>> = _motosRecomendadas


    val questions = listOf(
        Question(
            "¿Qué presupuesto tienes pensado para comprar una moto nueva?",
            listOf(
                "Menos de $7.000.000", "Entre $7.000.000 y $10.000.000",
                "Entre $10.000.000 y $15.000.000", "Más de $15.000.000"
            )
        ),
        Question(
            "¿Cuál será principalmente el uso de la moto?",
            listOf("Uso cotidiano en ciudad", "Viajes", "Trabajo", "Uso mixto")
        ),
        Question(
            "¿Qué tan importante es la eficiencia de combustible para ti?",
            listOf("Muy importante", "Moderadamente importante", "No es importante")
        ),
        Question(
            "¿Prefieres una moto con transmisión manual o automática?",
            listOf("Manual", "Semiautomática", "Automática", "No es relevante")
        ),
        Question(
            "¿Qué nivel de experiencia tienes como motociclista?",
            listOf("Principiante", "Intermedio", "Experto")
        ),
        Question(
            "¿Llevarás un segundo pasajero con frecuencia?",
            listOf("Sí", "No")
        ),
        Question(
            "¿Con qué frecuencia usarás la moto para trabajar?",
            listOf("Diariamente", "Algunas veces a la semana", "Ocasionalmente")
        )
    )

    fun handleAnswer(questionIndex: Int, answer: String) {
        viewModelScope.launch {
            _questionnaireState.update { currentState ->
                val updatedAnswers = currentState.answers.toMutableMap()
                updatedAnswers[questionIndex] = answer
                currentState.copy(
                    answers = updatedAnswers,
                    currentQuestionIndex = questionIndex + 1
                )
            }
        }
    }

    fun completeQuestionnaire() {
        viewModelScope.launch {
            // Procesar las respuestas del cuestionario
            val answers = _questionnaireState.value.answers
            // Obtener la lista filtrada de motos basada en las respuestas
            val filteredMotos = filterMotosBasedOnAnswers(answers)
            // Actualizar la lista de motos filtradas
            _motosRecomendadas.value = filteredMotos

            // Actualizar el estado con los resultados
            _questionnaireState.update { currentState ->
                currentState.copy(
                    isCompleted = true
                )
            }

        }
    }

    private fun filterMotosBasedOnAnswers(answers: Map<Int, String>): List<CategoriaMotos> {
        val motosList = (_motosResponse.value as? Response.Success)?.data ?: emptyList()

        return motosList.filter { moto ->
            val categoria: Map<String, String> = moto.ubicacionImagenes

            // Filtrado por presupuesto
            val filtroPresupuesto = when (answers[0]) {
                "Menos de $7.000.000" -> moto.precioActual < 7000000
                "Entre $7.000.000 y $10.000.000" -> moto.precioActual in 7000000..10000000
                "Entre $10.000.000 y $15.000.000" -> moto.precioActual in 10000000..15000000
                "Más de $15.000.000" -> moto.precioActual > 15000000
                else -> true
            }
            // Filtrado por uso principal de la moto
            val filtroUsoMoto = when (answers[1]) {
                "Uso cotidiano en ciudad" -> moto.ubicacionImagenes["carpeta3"].equals("SEMIAUTOMATICAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals("DEPORTIVA") ||
                        moto.ubicacionImagenes["carpeta3"].equals("URBANAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals("AUTOMATICAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals("TRABAJO") ||
                        moto.ubicacionImagenes["carpeta3"].equals("TODOTERRENO")

                "Viajes" -> moto.ubicacionImagenes["carpeta3"].equals("ADVENTURE")

                "Trabajo" -> moto.ubicacionImagenes["carpeta3"].equals("TRABAJO") ||
                        moto.ubicacionImagenes["carpeta3"].equals("URBANAS")

                "Uso mixto" -> true // Permitir todas
                else -> true
            }

            // Filtrado por importancia de la eficiencia de combustible
//            val filtroEficienciaCombustible = when (answers[2]) {
//                "Muy importante" -> moto.consumoPorGalon > 80
//                "Moderadamente importante" -> moto.consumoPorGalon in 50..80
//                "No es importante" -> true
//                else -> true
//            }

            // Filtrado por tipo de transmisión
            val filtroTransmision = when (answers[3]) {
                "Semiautomática" -> moto.ubicacionImagenes["carpeta3"].equals("SEMIAUTOMATICAS")
                "Automática" -> moto.ubicacionImagenes["carpeta3"].equals("AUTOMATICAS")
                "No es relevante" -> true
                else -> true
            }
//
//            // Filtrado por nivel de experiencia del motociclista
//            val filtroExperiencia = when (answers[4]) {
//                "Principiante" -> moto.cilindraje < 250 && moto.peso < 180
//                "Intermedio" -> moto.cilindraje in 250..500
//                "Experto" -> moto.cilindraje > 500 || moto.tipo == "Deportiva"
//                else -> true
//            }
//
//            // Filtrado por segundo pasajero
//            val filtroPasajero = when (answers[5]) {
//                "Si" -> moto.tieneAsientoParaPasajero == true || moto.cargaMaxima > 150
//                "No" -> true
//                else -> true
//            }
//
//            // Filtrado por uso para trabajo
//            val filtroUsoTrabajo = when (answers[6]) {
//                "Diariamente" -> moto.tipo == "Trabajo" || moto.durabilidad > 100000
//                "Algunas veces a la semana" -> true
//                "Ocasionalmente" -> true
//                else -> true
//            }

            // Combinación de todos los filtros
            filtroPresupuesto &&
                    filtroTransmision
//            filtroPresupuesto && filtroUsoPrincipal && filtroEficienciaCombustible &&
//                    filtroTransmision && filtroExperiencia && filtroPasajero && filtroUsoTrabajo
        }

    }
}