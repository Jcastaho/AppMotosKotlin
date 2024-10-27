package com.straccion.appmotos1.presentation.screens.vistabasededatos.vistas_database.database_eliminar_ocultar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import javax.inject.Inject

@HiltViewModel
class ElimRegistroViewModel @Inject constructor(
    private val dataBasesUsesCase: DataBasesUsesCase
): ViewModel() {
    //region Obtener datos de moto inicial
    private val _motosResponse = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()

    private val _allMotos = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    var busqueda by mutableStateOf("")
        private set

    init {
        getMotos()
    }

    private fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            dataBasesUsesCase.obtenerAllMotos().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _allMotos.value = response.data
                        filterMotos()
                    }
                    else -> _motosResponse.value = response
                }
            }
        } catch (e: Exception) {
            _motosResponse.value = Response.Failure(e)
        }
    }
    fun onSearchQueryChanged(query: String) {
        busqueda = query
        filterMotos()
    }
    private fun filterMotos() {
        val filteredList = if (busqueda.isBlank()) {
            _allMotos.value
        } else {
            _allMotos.value.filter { moto ->
                moto.id.contains(busqueda, ignoreCase = true) ||
                        moto.marcaMoto.contains(busqueda, ignoreCase = true)
            }
        }
        _motosResponse.value = Response.Success(filteredList)
    }
    //endregion

    //region Mensajes de confirmacion apra eliminar y ocultar
    private var _showConfirmDialog2 = MutableStateFlow(false)
    val showConfirmDialog2 = _showConfirmDialog2.asStateFlow()

    fun hideDialog2() {
        _showConfirmDialog2.value = false
    }

    fun showDialogForLimitedTime2() {
        viewModelScope.launch {
            _showConfirmDialog2.value = true
            delay(1250)
            _showConfirmDialog2.value = false
        }
    }

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
    //endregion

    //region Funcion para ocultar
    var ocultarMotoResponse by mutableStateOf<Response<Boolean>?>(null)
        private set
    fun ocultarMoto(visible: Boolean, motoId: String)= viewModelScope.launch {
        ocultarMotoResponse = Response.Loading
        val result = dataBasesUsesCase.ocultarMotocicleta(visible, motoId)
        ocultarMotoResponse = result
    }
    //endregion

    //region Funcion para eliminar la moto de la base de datos
    var eliminarMotoResponse by mutableStateOf<Response<Boolean>?>(null)
        private set
    fun eliminarMoto(motoId: String)= viewModelScope.launch {
        eliminarMotoResponse = Response.Loading
        val result = dataBasesUsesCase.eliminarMoto(motoId)
        eliminarMotoResponse = result
    }
    //endregion
}