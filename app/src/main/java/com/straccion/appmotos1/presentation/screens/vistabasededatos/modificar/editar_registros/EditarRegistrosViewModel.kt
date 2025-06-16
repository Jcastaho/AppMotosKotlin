package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.editar_registros

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.databases.DataBasesUsesCase
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class EditarRegistrosViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase,
    private val dataBasesUsesCase: DataBasesUsesCase,

): ViewModel(){

    //region Recibir datos de la moto.
    private val recibirCategoria: String = checkNotNull(savedStateHandle["moto"])
    private val encodedData: CategoriaMotos = Gson().fromJson(recibirCategoria, CategoriaMotos::class.java)

    private val _moto = MutableStateFlow<CategoriaMotos>(CategoriaMotos())
    val moto: StateFlow<CategoriaMotos> = _moto.asStateFlow()

    private val _motoById = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Loading)
    val motoById: StateFlow<Response<List<CategoriaMotos>>> = _motoById.asStateFlow()


    init {
        loadMotoDetails()
    }

    private fun loadMotoDetails() {
        viewModelScope.launch {
            if (encodedData.colores.isEmpty() || encodedData.imagenesPrincipales.isEmpty()) {
                loadMotoById()
            } else {
                _moto.value = encodedData
                _motoById.value = Response.Success(listOf(encodedData))
            }
        }
    }
    private fun loadMotoById() {
        viewModelScope.launch {
            val idMoto = encodedData.id
            try {
                obtenerMotosUsesCase.obtenerMotosById(idMoto).collect { response ->
                    _motoById.value = response
                    if (response is Response.Success && response.data.isNotEmpty()) {
                        _moto.value = response.data[0]
                    }
                }
            } catch (e: Exception) {
                _motoById.value = Response.Failure(e)
            }
        }
    }


    //endregion

    //region funciones para agregar/eliminar campo
    private val _fichaState = MutableStateFlow<Map<String, Any>>(emptyMap())
    val fichaState: StateFlow<Map<String, Any>> = _fichaState.asStateFlow()

    // Función para inicializar los datos de la ficha técnica (llamado al cargar la moto)
    fun setFichaTecnica(ficha: Map<String, Any>) {
        _fichaState.value = ficha.toMap() // Creamos una nueva copia del mapa
    }
    // Función para eliminar un campo
    fun eliminarCampo(clave: String) {
        _fichaState.value = _fichaState.value.filterKeys { it != clave }
    }
    fun agregarNuevoCampo() {
        _fichaState.value += ("Nuevo dato" to "")
    }
    fun modificarCampo(claveOriginal: String, nuevaClave: String, nuevoValor: String) {
        val nuevoMapa = _fichaState.value.toMutableMap()

        // Si es un campo nuevo
        if (claveOriginal == "Nuevo dato") {
            val sinEspacios = nuevaClave.replace(" ", "").lowercase()
            if (!nuevoMapa.contains(sinEspacios)) {
                nuevoMapa.remove("Nuevo dato")
                nuevoMapa[nuevaClave] = nuevoValor
            }
        }
        // Si es un campo existente
        else {
            if (claveOriginal != nuevaClave) {
                // Si cambió la clave
                nuevoMapa.remove(claveOriginal)
                nuevoMapa[nuevaClave] = nuevoValor
            } else {
                // Si solo cambió el valor
                nuevoMapa[claveOriginal] = nuevoValor
            }
        }
        _fichaState.value = nuevoMapa
    }

    //endregion

    //region boton Actualizar

    //mostrar mensaje por tiempo
    private var _showConfirmDialog = MutableStateFlow(false)
    val showConfirmDialog = _showConfirmDialog.asStateFlow()

    fun hideDialog() {
        _showConfirmDialog.value = false
    }

    fun showDialogForLimitedTime() {
        viewModelScope.launch {
            _showConfirmDialog.value = true
            delay(1250)
            _showConfirmDialog.value = false
        }
    }

    var updateResponse by mutableStateOf<Response<Boolean>?>(null)
        private set
    fun actualizar(idMoto: String, fichaItems: Map<String, Any>)= viewModelScope.launch {
        updateResponse = Response.Loading
        val result = dataBasesUsesCase.updateFichaTec(fichaItems, idMoto)
        updateResponse = result
    }

    //endregion

}