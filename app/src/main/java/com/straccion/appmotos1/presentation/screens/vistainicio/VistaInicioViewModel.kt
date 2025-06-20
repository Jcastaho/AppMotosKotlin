package com.straccion.appmotos1.presentation.screens.vistainicio

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.auth.AuthUsesCases
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VistaInicioViewModel @Inject constructor(
    private val authUsesCases: AuthUsesCases,
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
): ViewModel() {
    //guardar estado del lazy para volver a donde selecciono la moto
    val gridState = mutableStateOf(LazyGridState())

    //valida el ingreso a sesion
    var loginResponse by mutableStateOf<Response<FirebaseUser>?>(null)
        private set

    // Todas las motos
    private var _motosOriginales: List<CategoriaMotos>? = null
    val motosOriginales: List<CategoriaMotos>?
        get() = _motosOriginales

    //obtener las motos a mostrar
    private val _motosResponse = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()


    private val _allMotos = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    var busqueda by mutableStateOf("")
        private set

    init {
        login()
    }



    private fun login() = viewModelScope.launch {
        loginResponse = Response.Loading
        val result = authUsesCases.login()
        loginResponse = result
        getMotos()
    }
    private fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            obtenerMotosUsesCase.obtenerMotosVisibles().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _allMotos.value = response.data
                        // Guardar la lista original solo si aún no está guardada
                        if (_motosOriginales == null) {
                            _motosOriginales = response.data
                        }
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
