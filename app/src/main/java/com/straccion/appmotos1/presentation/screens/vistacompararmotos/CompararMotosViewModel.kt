package com.straccion.appmotos1.presentation.screens.vistacompararmotos

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.straccion.appmotos1.domain.model.CategoriaMotos
import com.straccion.appmotos1.domain.model.MotoSeleccionada
import com.straccion.appmotos1.domain.model.Response
import com.straccion.appmotos1.domain.use_cases.obtener_motos.ObtenerMotosUsesCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompararMotosViewModel @Inject constructor(
    private val obtenerMotosUsesCase: ObtenerMotosUsesCase
): ViewModel() {

    //region obtener las motos a mostrar
    private val _motosResponse = MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()

    init {
        getMotos()
    }

    fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            obtenerMotosUsesCase.obtenerMotos().collect { response ->
                _motosResponse.value = response
                if (response is Response.Success) {
                    _motosDisponibles.value = response.data.sortedBy { it.id }
                }
            }
        } catch (e: Exception) {
            _motosResponse.value = Response.Failure(e)
        }
    }
//endregion

    // region Seleccion de las motos a comparar
    private val _motosSeleccionadas = MutableStateFlow(List(3) { MotoSeleccionada() })
    val motosSeleccionadas: StateFlow<List<MotoSeleccionada>> = _motosSeleccionadas.asStateFlow()

    private val _motosDisponibles = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosDisponibles: StateFlow<List<CategoriaMotos>> = _motosDisponibles.asStateFlow()

    fun seleccionarMoto(index: Int, moto: CategoriaMotos) {
        val motoAnterior: CategoriaMotos?
        _motosSeleccionadas.value = _motosSeleccionadas.value.toMutableList().apply {
            motoAnterior = this[index].moto
            this[index] = MotoSeleccionada(moto = moto)
        }

        actualizarMotosDisponibles(moto, motoAnterior)
    }

    private fun actualizarMotosDisponibles(nuevaMoto: CategoriaMotos, motoAnterior: CategoriaMotos?) {
        _motosDisponibles.value = _motosDisponibles.value.toMutableList().apply {
            remove(nuevaMoto)
            motoAnterior?.let { add(it) }
        }.sortedBy { it.id }
    }
    //endregion
}