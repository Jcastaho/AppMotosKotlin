package com.straccion.appmotos1.presentation.screens.vistabasededatos.modificar.database_modificar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModRegistrosViewModel @Inject constructor(
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
): ViewModel() {

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
            obtenerMotosUsesCase.obtenerMotosVisibles().collect { response ->
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
}