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
) : ViewModel() {

    //region obtener las motos a mostrar
    private val _motosResponse =
        MutableStateFlow<Response<List<CategoriaMotos>>>(Response.Success(emptyList()))
    val motosResponse: StateFlow<Response<List<CategoriaMotos>>> = _motosResponse.asStateFlow()


    var busqueda by mutableStateOf("")
        private set

    // Estado para las motos filtradas
    private val _motosFiltradas = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosFiltradas: StateFlow<List<CategoriaMotos>> = _motosFiltradas.asStateFlow()


    private val _motosSeleccionadas = MutableStateFlow(List(3) { MotoSeleccionada() })
    val motosSeleccionadas: StateFlow<List<MotoSeleccionada>> = _motosSeleccionadas.asStateFlow()

    private val _motosDisponibles = MutableStateFlow<List<CategoriaMotos>>(emptyList())
    val motosDisponibles: StateFlow<List<CategoriaMotos>> = _motosDisponibles.asStateFlow()

    private val _allMotos = MutableStateFlow<List<CategoriaMotos>>(emptyList())

    init {
        getMotos()
    }

    fun getMotos() = viewModelScope.launch {
        _motosResponse.value = Response.Loading
        try {
            obtenerMotosUsesCase.obtenerMotosVisibles().collect { response ->
                _motosResponse.value = response
                if (response is Response.Success) {
                    _motosDisponibles.value = response.data.sortedBy { it.id }
                    _allMotos.value = response.data.sortedBy { it.id }
                    _motosFiltradas.value = _motosDisponibles.value
                }
            }
        } catch (e: Exception) {
            _motosResponse.value = Response.Failure(e)
        }
    }
//endregion


    // region Seleccion de las motos a comparar
    fun seleccionarMoto(index: Int, moto: CategoriaMotos) {
        val motosAnteriores: List<MotoSeleccionada> = _motosSeleccionadas.value
        _motosSeleccionadas.value = _motosSeleccionadas.value.toMutableList().apply {
            this[index] = MotoSeleccionada(moto = moto)
        }
        actualizarMotosDisponibles(_motosSeleccionadas.value, motosAnteriores)
    }

    private fun actualizarMotosDisponibles(
        motos: List<MotoSeleccionada>,
        motosAnteriores: List<MotoSeleccionada>
    ) {
        // 1. Obtener los IDs de las motos seleccionadas actualmente y anteriormente
        val idsSeleccionados = motos.mapNotNull { it.moto?.id }
        val idsAnteriores = motosAnteriores.mapNotNull { it.moto?.id }

        // 2. Motos que estaban seleccionadas pero ya no lo están
        val idsADesocultar = idsAnteriores - idsSeleccionados

        // 3. Filtrar la lista completa para excluir las motos seleccionadas actualmente
        val motosFiltradas = _allMotos.value.filter { moto ->
            moto.id !in idsSeleccionados
        }

        // 4. Actualizar _motosDisponibles con la lista filtrada
        _motosDisponibles.value = motosFiltradas

    }
    //endregion

    fun onSearchQueryChanged(query: String) {
        busqueda = query
        filterMotos()
    }

    private fun filterMotos() {
        _motosFiltradas.value = if (busqueda.isBlank()) {
            _motosDisponibles.value
        } else {
            _motosDisponibles.value.filter { moto ->
                moto.id.contains(busqueda, ignoreCase = true) ||
                        moto.marcaMoto.contains(busqueda, ignoreCase = true)
            }
        }
    }
}