package com.straccion.appmotos1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MotosViewModel : ViewModel() {

    private val _state = MutableStateFlow(MotosState())
    val state: StateFlow<MotosState> = _state.asStateFlow()


    private val _fichaItems = MutableStateFlow<List<Pair<String, Any>>>(emptyList())
    private val _fichaItemsMostrar = MutableStateFlow<List<Pair<String, Any>>>(emptyList())
    val fichaItemsEditar: StateFlow<List<Pair<String, Any>>> = _fichaItems.asStateFlow()
    val fichaItemsMostrar: StateFlow<List<Pair<String, Any>>> = _fichaItemsMostrar.asStateFlow()

    private val _questionnaireState = MutableStateFlow(QuestionnaireState())
    val questionnaireState = _questionnaireState.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _selectedItems = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val itemsSeleccionados: StateFlow<List<CategoriaMotos>> = _selectedItems

    private val _isCargar = MutableStateFlow(true) // Comienza en true para mostrar el ProgressBar
    val isCargar: StateFlow<Boolean> get() = _isCargar

    //Estos son para la recomendacion
    private val _motosRecomendadas = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosRecomendadas: StateFlow<List<CategoriaMotos>> = _motosRecomendadas

    fun cargarDatos() {
        viewModelScope.launch {
            _isCargar.value = true // Resetea a true cuando se selecciona una nueva moto
            delay(900) // Simula una carga asíncrona de 2 segundos
            _isCargar.value = false // Una vez que los datos estén listos, actualiza el estado
        }
    }

    init {
        loadMotos()
    }
    private fun addToSelected(moto: CategoriaMotos) {
        if (!_selectedItems.value.contains(moto)) {
            _selectedItems.value += moto
        }
    }

    private fun removeFromSelected(moto: CategoriaMotos) {
        _selectedItems.value -= moto
    }

    fun toggleMotoSelection(moto: CategoriaMotos) {
        if (_selectedItems.value.contains(moto)) {
            removeFromSelected(moto)
        } else {
            addToSelected(moto)
        }
    }

    fun deselectAllMotos() {
        _selectedItems.value = emptyList()
    }

    fun selectAllMotos(motos: List<CategoriaMotos>) {
        _selectedItems.value = motos
    }

    fun toggleFavorite(motoId: String) {
        viewModelScope.launch {
            val currentFavoriteState = _isFavorite.value
            val newFavoriteState = !currentFavoriteState

            // Actualizar el estado local
            _isFavorite.value = newFavoriteState

            // Actualizar en Firebase
            try {
                val result = actualizarFavoritos(motoId, newFavoriteState)
                if (result) {
                    // Actualizar el estado de la moto seleccionada
                    _state.update { currentState ->
                        val updatedMoto =
                            currentState.selectedMotos?.copy(favoritos = newFavoriteState)
                        currentState.copy(selectedMotos = updatedMoto)
                    }
                } else {
                    // Si la actualización en Firebase falla, revertir el estado local
                    _isFavorite.value = currentFavoriteState
                    _state.update {
                        it.copy(
                            showDialog = true,
                            dialogInfo = DialogInfo(
                                title = "Error",
                                message = "No se pudo actualizar el estado de favorito. Por favor, intente nuevamente.",
                                isSuccess = false,
                                gifResourceId = R.drawable.gif_confirmar
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                // Manejar la excepción
                _isFavorite.value = currentFavoriteState
                _state.update {
                    it.copy(
                        showDialog = true,
                        dialogInfo = DialogInfo(
                            title = "Error",
                            message = "Ocurrió un error: ${e.message}",
                            isSuccess = false,
                            gifResourceId = R.drawable.gif_confirmar
                        )
                    )
                }
            }
        }
    }

    // Función para actualizar el estado de favorito cuando se selecciona una moto
    fun updateFavoriteState(isFavorite: Boolean) {
        _isFavorite.value = isFavorite
    }

    private fun loadMotos() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            try {
                getMotos() { motos ->
                    val visibleMotos = motos.filter { it.visible }
                    _state.value = _state.value.copy(
                        motos = motos,
                        filteredMotos = visibleMotos,
                        isLoading = false,
                        errorMessage = if (motos.isEmpty()) "No se encontraron motos" else null
                    )
                }
                getMotosFav { motosFav, uidUser ->
                    _state.value = uidUser?.let {
                        _state.value?.copy(
                            uidUser = it,
                            motosFavoritas = motosFav
                        )
                    }!!
                }
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Error: ${e.message}"
                )
            }
        }
    }

    fun updateSearchQuery(query: String) {
        val currentState = _state.value

        val filteredMotos = currentState.motos.filter { moto ->
            moto.visible && (
                    moto.id.contains(query, ignoreCase = true) ||
                            moto.marcaMoto.contains(query, ignoreCase = true)
                    )
        }

        _state.value = currentState.copy(
            searchQuery = query,
            filteredMotos = filteredMotos
        )
    }

    fun busquedaBarraEliminarBaseDatos(query: String) {
        val currentState = _state.value

        val filteredMotos = currentState.motos.filter { moto ->
            moto.id.contains(query, ignoreCase = true) ||
                    moto.marcaMoto.contains(query, ignoreCase = true)

        }

        _state.value = currentState.copy(
            searchQuery = query,
            filteredMotos = filteredMotos
        )
    }

    enum class AccionMoto {
        ACTUALIZAR_VISIBILIDAD,
        ELIMINAR
    }

    fun actualizarOEliminarMoto(motoId: String, accion: AccionMoto, nuevoEstado: Boolean? = null) {
        viewModelScope.launch {
            try {
                val resultado = when (accion) {
                    AccionMoto.ACTUALIZAR_VISIBILIDAD -> actualizarVisibilidadEnFirebase(
                        motoId,
                        nuevoEstado ?: return@launch
                    )

                    AccionMoto.ELIMINAR -> eliminarDocumentoEnFirebase(motoId)
                }
                if (resultado) {
                    _state.update { currentState ->
                        val motosActualizadas = when (accion) {
                            AccionMoto.ACTUALIZAR_VISIBILIDAD -> currentState.motos.map { moto ->
                                if (moto.id == motoId) moto.copy(visible = nuevoEstado!!) else moto
                            }

                            AccionMoto.ELIMINAR -> currentState.motos.filter { it.id != motoId }
                        }

                        currentState.copy(
                            motos = motosActualizadas,
                            filteredMotos = motosActualizadas.filter { moto ->
                                moto.visible && (
                                        moto.id.contains(
                                            currentState.searchQuery,
                                            ignoreCase = true
                                        ) ||
                                                moto.marcaMoto.contains(
                                                    currentState.searchQuery,
                                                    ignoreCase = true
                                                )
                                        )
                            },
                            showDialog = true,
                            dialogInfo = DialogInfo(
                                title = when (accion) {
                                    AccionMoto.ACTUALIZAR_VISIBILIDAD -> "Actualización Exitosa"
                                    AccionMoto.ELIMINAR -> "Eliminación Exitosa"
                                },
                                message = when (accion) {
                                    AccionMoto.ACTUALIZAR_VISIBILIDAD -> "La visibilidad se ha actualizado correctamente."
                                    AccionMoto.ELIMINAR -> "El documento ha sido eliminado correctamente."
                                },
                                isSuccess = true,
                                gifResourceId = R.drawable.gif_confirmar
                            )
                        )
                    }
                } else {
                    _state.update {
                        it.copy(
                            showDialog = true,
                            dialogInfo = DialogInfo(
                                title = when (accion) {
                                    AccionMoto.ACTUALIZAR_VISIBILIDAD -> "Error en la Actualización"
                                    AccionMoto.ELIMINAR -> "Error en la Eliminación"
                                },
                                message = when (accion) {
                                    AccionMoto.ACTUALIZAR_VISIBILIDAD -> "No se pudo actualizar la visibilidad. Por favor, intente nuevamente."
                                    AccionMoto.ELIMINAR -> "No se pudo eliminar el documento. Por favor, intente nuevamente."
                                },
                                isSuccess = false,
                                gifResourceId = R.drawable.gif_confirmar
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        showDialog = true,
                        dialogInfo = DialogInfo(
                            title = "Error",
                            message = "Ocurrió un error: ${e.message}",
                            isSuccess = false,
                            gifResourceId = R.drawable.gif_confirmar
                        )
                    )
                }
            }
        }
    }

    fun actualizarItem(index: Int, clave: String, valor: Any) {
        _fichaItems.update { currentList ->
            currentList.toMutableList().apply {
                this[index] = clave to valor
            }
        }
    }

    fun agregarNuevoCampo() {
        _fichaItems.update { currentList ->
            currentList + ("Nuevo campo" to "")
        }
    }

    fun eliminarCampo(index: Int) {
        _fichaItems.update { currentList ->
            val newList = currentList.toMutableList().apply {
                removeAt(index)
            }
            newList
        }
    }

    fun actualizarFichaTecnica() {
        viewModelScope.launch {
            val nuevoMapa = _fichaItems.value.toMap()
            val motoId = _state.value.selectedMotos?.id
            if (motoId != null) {
                val resultado = actualizarFichaTecnicayOtrosDatosEnFirebase(motoId, nuevoMapa)
                if (resultado) {
                    _state.value = _state.value.copy(
                        showDialog = true,
                        dialogInfo = DialogInfo(
                            title = "Actualización Exitosa",
                            message = "La ficha técnica se ha actualizado correctamente.",
                            isSuccess = true,
                            gifResourceId = R.drawable.gif_confirmar
                        )
                    )
                } else {
                    _state.value = _state.value.copy(
                        showDialog = true,
                        dialogInfo = DialogInfo(
                            title = "Error en la Actualización",
                            message = "No se pudo actualizar la ficha técnica. Por favor, intente nuevamente.",
                            isSuccess = false,
                            gifResourceId = R.drawable.gif_confirmar
                        )
                    )
                }
            } else {
                _state.value = _state.value.copy(
                    showDialog = true,
                    dialogInfo = DialogInfo(
                        title = "Error",
                        message = "No se ha seleccionado ninguna moto.",
                        isSuccess = false,
                        gifResourceId = R.drawable.gif_confirmar // Reemplaza con el nombre de tu GIF
                    )
                )
            }
        }
    }

    fun dismissDialog() {
        _state.value = _state.value.copy(showDialog = false, dialogInfo = null)
    }

    fun selectMotoById(id: String) {
        viewModelScope.launch {
            val moto = _state.value.motos.find { it.id == id }
            if (moto != null) {
                selectMoto(moto)
            }
        }
    }

    fun selectMoto(moto: CategoriaMotos) {
        viewModelScope.launch {
            val firstColor = moto.colores.firstOrNull()
            _state.value = _state.value.copy(
                selectedMotos = moto,
                imagenesMostradas = moto.imagenesPrincipales,
                colorSeleccionado = firstColor, // Selecciona el primer color por defecto
                imagenesAdicionales = moto.caracteristicasImagenes,
                caracteristicasTexto = moto.caracteristicasTexto,
                ubicacionImagenes = moto.ubicacionImagenes
            )
            _fichaItems.value = moto.fichaTecnica.toList()
            _fichaItemsMostrar.value = moto.fichaTecnica.toList()
            if (_motosRecomendadas.value.isNotEmpty()){
                TextoRecomendacion(moto)
            }

            agregarMapFaltante(moto)
            // Si hay un color seleccionado, actualiza las imágenes
            firstColor?.let { selectColor(it) }
        }
    }

    fun agregarMapFaltante(moto: CategoriaMotos) {
        val mapAdd = mapOf(
            "prioridad" to moto.prioridad,
            "precioActual" to moto.precioActual,
            "precioAnterior" to moto.precioAnterior,
            "diferenciaValor" to moto.diferenciaValor,
            "descuento" to moto.descuento,
            "consumoPorGalon" to moto.consumoPorGalon,
            "velocidadMaxima" to moto.velocidadMaxima
        )
        val listaMapAdd = mapAdd.toList()

        // Reemplazar los valores en lugar de agregarlos si la clave ya existe
        val newFichaItems = _fichaItems.value.toMutableList()

        listaMapAdd.forEach { newItem ->
            val index = newFichaItems.indexOfFirst { it.first == newItem.first }
            if (index >= 0) {
                // Reemplaza el valor existente
                newFichaItems[index] = newItem
            } else {
                // Agrega el nuevo par clave-valor
                newFichaItems.add(newItem)
            }
        }
        // Actualiza la lista en _fichaItems
        _fichaItems.value = newFichaItems
    }

    fun selectColor(color: String) {
        viewModelScope.launch {
            val moto = _state.value.selectedMotos ?: return@launch
            val imagenesMostradas = when (color) {
                moto.colores.getOrNull(0) -> moto.imagenesPrincipales
                moto.colores.getOrNull(1) -> moto.imagenesColores1
                moto.colores.getOrNull(2) -> moto.imagenesColores2
                moto.colores.getOrNull(3) -> moto.imagenesColores3
                moto.colores.getOrNull(4) -> moto.imagenesColores4
                moto.colores.getOrNull(5) -> moto.imagenesColores5
                moto.colores.getOrNull(6) -> moto.imagenesColores6
                else -> moto.imagenesPrincipales
            }
            _state.value = _state.value.copy(
                colorSeleccionado = color,
                imagenesMostradas = imagenesMostradas
            )
        }
    }

    fun selectOption(optionType: String, value: String) {
        _state.value = when (optionType) {
            "marca" -> _state.value.copy(selectedMarca = value)
            "fabricante" -> _state.value.copy(selectedFrabricante = value)
            "categoria" -> _state.value.copy(selectedCategoriasMotos = value)
            "nombre" -> _state.value.copy(nombreMotoaRegistrar = value)
            else -> _state.value // No hacer nada si el tipo de opción no es reconocido
        }
    }

    fun listaMarcas() {
        val moto = _state.value.motos
        _state.value = _state.value.copy(
            listaFrabricanteMotos = moto.mapNotNull { moto ->
                moto.ubicacionImagenes["carpeta1"]
            },
            listaMarcasMotos = moto.mapNotNull { moto ->
                moto.ubicacionImagenes["carpeta2"]
            },
            listaCategoriasMotos = moto.mapNotNull { moto ->
                moto.ubicacionImagenes["carpeta3"]
            }
        )
    }

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

    fun TextoRecomendacion(motosFiltradas: CategoriaMotos?){
        // Generar una recomendación personalizada basada en las motos filtradas
        val answers = _questionnaireState.value.answers
        val motoRecomendacion = generatePersonalizedRecommendation(motosFiltradas, answers)

        // Actualizar el estado con los resultados
        _questionnaireState.update { currentState ->
            currentState.copy(
                isCompleted = true,
                personalizedRecommendation = motoRecomendacion.second
            )
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
            _questionnaireState.update{ currentState ->
                currentState.copy(
                    isCompleted = true
                )
            }

        }
    }
    fun ReiniciarQuestionnaire(){
        _questionnaireState.update{ currentState ->
            currentState.copy(
                personalizedRecommendation = null,
                isCompleted = false
            )
        }
    }

    private fun filterMotosBasedOnAnswers(answers: Map<Int, String>): List<CategoriaMotos> {
        return _state.value.motos.filter { moto ->
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
                "Uso cotidiano en ciudad" -> moto.ubicacionImagenes["carpeta3"].equals( "SEMIAUTOMATICAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "DEPORTIVA") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "URBANAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "AUTOMATICAS") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "TRABAJO") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "TODOTERRENO")

                "Viajes" -> moto.ubicacionImagenes["carpeta3"].equals( "ADVENTURE")

                "Trabajo" -> moto.ubicacionImagenes["carpeta3"].equals( "TRABAJO") ||
                        moto.ubicacionImagenes["carpeta3"].equals( "URBANAS")
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
                "Semiautomática" -> moto.ubicacionImagenes["carpeta3"].equals( "SEMIAUTOMATICAS")
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

// Función para generar la recomendación personalizada
private fun generatePersonalizedRecommendation(
    motosFiltradas: CategoriaMotos?,
    answers: Map<Int, String>
): Pair<String?, String> {
    if (motosFiltradas == null) {
        return "" to ""
    }
    // Obtener la primera moto filtrada para el ejemplo, pero puedes usar otras lógicas
    val motoSeleccionada = motosFiltradas

    // Crear una recomendación personalizada basada en las respuestas y la ficha técnica de la moto
    val recomendacion = StringBuilder()

    // Agregar lógica personalizada
    if (answers[2] == "Muy importante" && motoSeleccionada.consumoPorGalon < 30) {
        recomendacion.append("Esta moto es altamente eficiente en consumo de combustible, con un rendimiento de ${motoSeleccionada.consumoPorGalon} km/galón. ")
    } else if (answers[2] == "Moderadamente importante" && motoSeleccionada.consumoPorGalon in 30..40) {
        recomendacion.append("Esta moto tiene una eficiencia de combustible moderada, con un consumo de ${motoSeleccionada.consumoPorGalon} km/galón. ")
    }

    // Basado en la velocidad máxima
    if (answers[1] == "Viajes" && motoSeleccionada.velocidadMaxima > 120) {
        recomendacion.append("Es ideal para largos viajes, con una velocidad máxima de ${motoSeleccionada.velocidadMaxima} km/h. ")
    } else if (answers[1] == "Uso cotidiano en ciudad" && motoSeleccionada.velocidadMaxima < 120) {
        recomendacion.append("Perfecta para moverte por la ciudad, con una velocidad adecuada hasta de ${motoSeleccionada.velocidadMaxima} km/h. ")
    }

    // Basado en la experiencia del motociclista
    when (answers[4]) {
        "Principiante" -> recomendacion.append("Esta moto es ideal para principiantes, con características fáciles de manejar. ")
        "Intermedio" -> recomendacion.append("Para motociclistas intermedios, esta moto ofrece un buen equilibrio entre potencia y control. ")
        "Experto" -> recomendacion.append("Si eres un motociclista experto, esta moto te brindará la potencia y velocidad que buscas. ")
    }

    // Puedes seguir agregando más personalizaciones aquí basadas en las características de la moto.

    // Retorna la categoría recomendada y la descripción personalizada
    val categoria: Map<String, String> = motoSeleccionada.ubicacionImagenes
    return categoria.get("carpeta3") to recomendacion.toString()
        .ifBlank { "Esta moto tiene características que se adaptan a tus necesidades." }
}


// Nueva clase de estado del cuestionario que incluye la recomendación personalizada
data class QuestionnaireState(
    val answers: Map<Int, String> = emptyMap(),
    val currentQuestionIndex: Int = 0,
    val isCompleted: Boolean = false,
    val personalizedRecommendation: String? = null // Agregar esta nueva propiedad
)

data class MotosState(
    val motos: List<CategoriaMotos> = emptyList(),
    val filteredMotos: List<CategoriaMotos> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val selectedMotos: CategoriaMotos? = null,
    val colorSeleccionado: String? = null,
    val imagenesAdicionales: List<String> = emptyList(),
    val imagenesMostradas: List<String> = emptyList(),
    val caracteristicasTexto: List<String> = emptyList(),
    var ubicacionImagenes: Map<String, Any> = mapOf(),
    val listaFrabricanteMotos: List<String> = emptyList(),
    val listaMarcasMotos: List<String> = emptyList(),
    val listaCategoriasMotos: List<String> = emptyList(),
    val selectedFrabricante: String? = null,
    val selectedMarca: String? = null,
    val selectedCategoriasMotos: String? = null,
    val nombreMotoaRegistrar: String? = null,
    val showDialog: Boolean = false,
    val dialogInfo: DialogInfo? = null,
    val isQuestionnaireCompleted: Boolean = false,
    val recommendedCategory: String? = null,
    val motosFavoritas: List<String> = emptyList(),
    val uidUser: String = "",

)

data class DialogInfo(
    val title: String,
    val message: String,
    val isSuccess: Boolean,
    val gifResourceId: Int
)
