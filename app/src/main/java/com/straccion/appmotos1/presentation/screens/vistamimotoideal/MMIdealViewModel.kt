package com.straccion.appmotos1.presentation.screens.vistamimotoideal

import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.QuestionnaireState
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.model.RespuestaGemini
import com.straccion.appmotos1.domain.use_cases.iamessage.IaMessageUseCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class MMIdealViewModel @Inject constructor(
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase,
    private val iaMessageUseCase: IaMessageUseCase
) : ViewModel() {

    private val _response = mutableStateOf("")
    val responseAI: String
        get() = _response.value


    //guardar estado del lazy para volver a donde selecciono la moto
    val gridState = mutableStateOf(LazyGridState())

    //obtener las motos a mostrar
    private val _motosResponse =
        MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
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

    private val _motosRecomendadasPosibles = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosRecomendadasPosibles: StateFlow<List<CategoriaMotos>> = _motosRecomendadasPosibles


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

    @RequiresApi(35)
    fun completeQuestionnaire() {
        viewModelScope.launch {
            // Procesar las respuestas del cuestionario
            val answers = _questionnaireState.value.answers
            // Obtener la lista filtrada de motos basada en las respuestas
            val filtrado = recomendarMotos(answers)
            val allMotos = (_motosResponse.value as? Response.Success)?.data ?: emptyList()
            val prompt = Prompts(answers, filtrado, allMotos)
            val respuestaIA = sendPrompt(prompt).await()
            val (recomendacion, recomendacionPosibles) = decodeRespuestaGemini(respuestaIA.trimIndent(), allMotos)


            //      Actualizar la lista de motos filtradas
            _motosRecomendadas.value = recomendacion
            _motosRecomendadasPosibles.value = recomendacionPosibles

            //   Actualizar el estado con los resultados
            _questionnaireState.update { currentState ->
                currentState.copy(
                    isCompleted = true
                )
            }

        }
    }

    fun recomendarMotos(answers: Map<Int, String>): List<String> {
        var motosFiltradas = (_motosResponse.value as? Response.Success)?.data ?: emptyList()

        // Filtro por presupuesto
        when (answers[0]) {
            "Menos de $7.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual < 7000000 }

            "Entre $7.000.000 y $10.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual in 7000000..10000000 }

            "Entre $10.000.000 y $15.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual in 10000000..15000000 }

            "Más de $15.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual > 15000000 }
        }

        // Filtro por propósito
        when (answers[1]) {
            "Uso cotidiano en ciudad" -> motosFiltradas =
                motosFiltradas.filter {
                    it.ubicacionImagenes["carpeta3"] == "URBANAS" ||
                            it.ubicacionImagenes["carpeta3"] == "AUTOMATICAS" ||
                            it.ubicacionImagenes["carpeta3"] == "DEPORTIVA" ||
                            it.ubicacionImagenes["carpeta3"] == "TRABAJO" ||
                            it.ubicacionImagenes["carpeta3"] == "SEMIAUTOMATICAS" ||
                            it.ubicacionImagenes["carpeta3"] == "TODOTERRENO"
                }

            "Viajes" -> motosFiltradas = motosFiltradas.filter {
                it.ubicacionImagenes["carpeta3"] == "ADVENTURE" ||
                        it.ubicacionImagenes["carpeta3"] == "TODOTERRENO"
            }

            "Trabajo" -> motosFiltradas = motosFiltradas.filter {
                it.ubicacionImagenes["carpeta3"] == "TRABAJO" ||
                        it.ubicacionImagenes["carpeta3"] == "URBANAS" ||
                        it.ubicacionImagenes["carpeta3"] == "TODOTERRENO"
            }

            "Uso mixto" -> motosFiltradas = motosFiltradas.filter {
                it.ubicacionImagenes["carpeta3"] == "URBANAS" ||
                        it.ubicacionImagenes["carpeta3"] == "TODOTERRENO" ||
                        it.ubicacionImagenes["carpeta3"] == "DEPORTIVA" ||
                        it.ubicacionImagenes["carpeta3"] == "TRABAJO"
            }
        }

        // Filtro por transmisión
        when (answers[3]) {
            "Manual" -> motosFiltradas = motosFiltradas.filter {
                it.ubicacionImagenes["carpeta3"] != "SEMIAUTOMATICAS" &&
                        it.ubicacionImagenes["carpeta3"] != "AUTOMATICAS"
            }

            "Semiautomática" -> motosFiltradas =
                motosFiltradas.filter { it.ubicacionImagenes["carpeta3"] == "SEMIAUTOMATICAS" }

            "Automática" -> motosFiltradas =
                motosFiltradas.filter { it.ubicacionImagenes["carpeta3"] == "AUTOMATICAS" }

            "No es relevante" -> motosFiltradas = motosFiltradas // No se aplica filtro
        }

        // Filtro por frecuencia de uso para trabajar
        when (answers[6]) {
            "Diariamente" -> motosFiltradas = motosFiltradas.filter {
                it.ubicacionImagenes["carpeta3"] == "URBANAS" ||
                        it.ubicacionImagenes["carpeta3"] == "TODOTERRENO" ||
                        it.ubicacionImagenes["carpeta3"] == "TRABAJO"
            }

            "Algunas veces a la semana" -> motosFiltradas =
                motosFiltradas.filter {
                    it.ubicacionImagenes["carpeta3"] == "URBANAS" ||
                            it.ubicacionImagenes["carpeta3"] == "TODOTERRENO" ||
                            it.ubicacionImagenes["carpeta3"] == "DEPORTIVA" ||
                            it.ubicacionImagenes["carpeta3"] == "TRABAJO"
                }

            "Ocasionalmente" -> motosFiltradas = motosFiltradas // No se aplica filtro
        }

        // Devolver los IDs de las motos recomendadas
        return motosFiltradas.map { it.id }
    }

    fun Prompts(answers: Map<Int, String>, filtrado: List<String>, allMotos: List<CategoriaMotos>): String {
        var prompt =
            "Mira esta lista de motos $filtrado necesito que en base a esas motos, me des una lista recomendando las motos que mas se adapten a las siguientes caracterisitcas:  "

        // Filtro por eficiencia de combustible
        val combustible = when (answers[2]) {
            "Muy importante" -> "Para el conductor, la eficiencia del combustible es muy importante."

            "Moderadamente importante" -> "Para el conductor, la eficiencia del combustible es moderadamente importante."

            "No es importante" -> "Para el conductor, la eficiencia del combustible no es un factor relevante."
            else -> ""
        }
        //Filtro por nivel de experiencia
        val experiencia = when (answers[4]) {
            "Principiante" -> "Es un conductor principiante y preferiblemente desea una moto que sea facil de manejar y maniobrar."

            "Intermedio" -> "Es un conductor con experienca relativa, no es un experto, pero ya sabe muchas cosas a la hora de manejar."

            "Experto" -> "Es un conductor experto, le puedes recomendar cualquier moto sin importar el nivel de dificultad a la hora de manejarla."
            else -> ""
        }

        //  Filtro por capacidad de pasajeros
        val pasajero = when (answers[5]) {
            "Sí" -> "Es un conductor que lleva frecuente un segundo pasajero, por ende, debes de tener en cuenta el tamaño del sillin a la hora de recomendar"
            "No" -> "Es un conductor que viaja solo, por ende no es importate el tamaño del sillin"
            else -> ""
        }
        prompt =
            "$prompt$combustible $experiencia $pasajero. "

        var  motosFiltradas = allMotos
        // Filtro por presupuesto
        when (answers[0]) {
            "Menos de $7.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual <= 10000000 }

            "Entre $7.000.000 y $10.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual in 5000000..15000000 }

            "Entre $10.000.000 y $15.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual in 8000000..20000000 }

            "Más de $15.000.000" -> motosFiltradas =
                motosFiltradas.filter { it.precioActual > 10000000 }
        }
        val motos: List<String> = allMotos.map { it.id }
        prompt =
            "$prompt Ahora, crea una segunda lista, necesito que en base a estas nuevas motos, me des otra lista recomendando las motos que mas se adapten a las caracteristicas anteriores: $motos Recuerda que son dos listas diferentes en formato JSON, La primera se llama lista1 y la segunda lista2. No incluyas texto adicional, solo responde con el JSON válido"
        Log.d("prompt", "D: $prompt")
        return prompt
    }


    @RequiresApi(35)
    fun sendPrompt(prompt: String): Deferred<String> = viewModelScope.async {
        try {
            val result = iaMessageUseCase.aiMessage(prompt)
            Log.d("respuesta", "" + result)
            _response.value = result // Asigna el resultado a _response.value
            result // Devuelve el resultado
        } catch (e: Exception) {
            _response.value = "Error: ${e.message}"
            "null" // Devuelve "null" en caso de error
        }
    }

    fun decodeRespuestaGemini(
        respuesta: String,
        allMotos: List<CategoriaMotos>
    ): Pair<List<CategoriaMotos>, List<CategoriaMotos>> {
        val jsonWithoutCodeBlocks = respuesta
            .replace("```json", "")
            .replace("```", "")
            .trim() // Elimina espacios en blanco al inicio y al final

        val respuestaGemini = Json.decodeFromString<RespuestaGemini>(jsonWithoutCodeBlocks)
        val recomendacion = allMotos.filter { it.id in respuestaGemini.lista2 }
        val recomendacionPosibles = allMotos.filter { it.id in respuestaGemini.lista2 }

        return Pair(recomendacion, recomendacionPosibles)
    }
}